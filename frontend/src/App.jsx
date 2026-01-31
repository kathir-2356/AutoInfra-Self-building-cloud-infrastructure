import React, { useState, useEffect } from 'react'
import axios from 'axios'
import { mockBackend } from './mockBackend'
import './App.css'

const USE_MOCK = false // Set to false when backend is running

function App() {
  const [githubUrl, setGithubUrl] = useState('')
  const [deploying, setDeploying] = useState(false)
  const [deployments, setDeployments] = useState([])
  const [currentDeployment, setCurrentDeployment] = useState(null)
  const [metrics, setMetrics] = useState({
    responseTime: 2.4,
    requestsPerMin: 1200,
    cpuUsage: 45,
    activeInstances: 12,
    uptime: 99.9,
    monthlyCost: 127.45,
    projectedCost: 156.20
  })
  const [securityStatus, setSecurityStatus] = useState({
    sslEnabled: true,
    wafProtection: true,
    ddosShield: true,
    vulnerabilityScan: false
  })
  const [alerts, setAlerts] = useState([
    { type: 'warning', message: 'High CPU usage on prod-server-01' },
    { type: 'info', message: 'Scheduled maintenance in 2 hours' }
  ])

  useEffect(() => {
    fetchDeployments()
    
    // Update metrics every 5 seconds
    const metricsInterval = setInterval(() => {
      setMetrics(prev => ({
        ...prev,
        responseTime: (Math.random() * 2 + 1.5).toFixed(1),
        requestsPerMin: Math.floor(Math.random() * 500 + 1000),
        cpuUsage: Math.floor(Math.random() * 30 + 40),
        activeInstances: Math.floor(Math.random() * 5 + 10)
      }))
    }, 5000)
    
    return () => clearInterval(metricsInterval)
  }, [])

  const fetchDeployments = async () => {
    try {
      if (USE_MOCK) {
        const data = await mockBackend.getAllDeployments()
        setDeployments(data)
      } else {
        const response = await axios.get('/api/deployments')
        setDeployments(response.data)
      }
    } catch (error) {
      console.error('Failed to fetch deployments:', error)
    }
  }

  const handleDeploy = async () => {
    if (!githubUrl) return
    
    setDeploying(true)
    try {
      let deploymentId
      
      if (USE_MOCK) {
        const response = await mockBackend.deploy(githubUrl)
        deploymentId = response.deploymentId
      } else {
        const response = await axios.post('/api/deploy', {
          githubUrl,
          userId: 'user-' + Date.now()
        })
        deploymentId = response.data.deploymentId
      }
      
      setCurrentDeployment(deploymentId)
      
      // Poll for status updates
      const pollInterval = setInterval(async () => {
        try {
          let status
          
          if (USE_MOCK) {
            status = await mockBackend.getDeploymentStatus(deploymentId)
          } else {
            const statusResponse = await axios.get(`/api/deployment/${deploymentId}/status`)
            status = statusResponse.data
          }
          
          if (status && (status.status === 'COMPLETED' || status.status === 'FAILED')) {
            clearInterval(pollInterval)
            setDeploying(false)
            fetchDeployments()
          }
        } catch (error) {
          console.error('Failed to fetch deployment status:', error)
          clearInterval(pollInterval)
          setDeploying(false)
        }
      }, 2000)
      
    } catch (error) {
      alert('Deployment failed: ' + error.message)
      setDeploying(false)
    }
  }

  const getStatusColor = (status) => {
    switch (status) {
      case 'COMPLETED': return '#22c55e'
      case 'FAILED': return '#ef4444'
      case 'IN_PROGRESS': return '#f59e0b'
      default: return '#6b7280'
    }
  }

  return (
    <div className="app">
      {/* Advanced Header */}
      <header className="advanced-header">
        <div className="header-content">
          <div className="logo-section">
            <div className="logo">🚀</div>
            <div className="brand">
              <h1>AutoInfra Enterprise</h1>
              <span className="tagline">AI-Powered Cloud Deployment Platform</span>
            </div>
          </div>
          <div className="header-stats">
            <div className="stat">
              <span className="stat-value">{deployments.length}</span>
              <span className="stat-label">Deployments</span>
            </div>
            <div className="stat">
              <span className="stat-value">{deployments.filter(d => d.status === 'COMPLETED').length}</span>
              <span className="stat-label">Success</span>
            </div>
            <div className="stat">
              <span className="stat-value">{metrics.uptime}%</span>
              <span className="stat-label">Uptime</span>
            </div>
          </div>
        </div>
      </header>

      <div className="dashboard">
        {/* Sidebar Navigation */}
        <nav className="sidebar">
          <div className="nav-item active">
            <span className="nav-icon">🚀</span>
            <span>Deploy</span>
          </div>
          <div className="nav-item">
            <span className="nav-icon">📊</span>
            <span>Monitoring</span>
          </div>
          <div className="nav-item">
            <span className="nav-icon">🔒</span>
            <span>Security</span>
          </div>
          <div className="nav-item">
            <span className="nav-icon">💰</span>
            <span>Cost</span>
          </div>
          <div className="nav-item">
            <span className="nav-icon">⚙️</span>
            <span>Settings</span>
          </div>
        </nav>

        {/* Main Content */}
        <main className="main-content">
          {/* Quick Actions */}
          <div className="quick-actions">
            <div className="action-card primary">
              <h3>🚀 Quick Deploy</h3>
              <div className="deploy-form">
                <div className="input-group">
                  <input
                    type="text"
                    placeholder="GitHub repository URL"
                    value={githubUrl}
                    onChange={(e) => setGithubUrl(e.target.value)}
                    disabled={deploying}
                    className="advanced-input"
                  />
                  <button 
                    onClick={handleDeploy}
                    disabled={deploying || !githubUrl}
                    className="deploy-btn"
                  >
                    {deploying ? '⏳ Deploying...' : '🚀 Deploy'}
                  </button>
                </div>
                <div className="deploy-options">
                  <label><input type="checkbox" /> Auto-scaling</label>
                  <label><input type="checkbox" /> SSL Certificate</label>
                  <label><input type="checkbox" /> CDN</label>
                </div>
              </div>
            </div>

            {/* Real-time Metrics */}
            <div className="metrics-grid">
              <div className="metric-card">
                <div className="metric-icon">📈</div>
                <div className="metric-info">
                  <span className="metric-value">{metrics.responseTime}s</span>
                  <span className="metric-label">Avg Response</span>
                </div>
              </div>
              <div className="metric-card">
                <div className="metric-icon">🔥</div>
                <div className="metric-info">
                  <span className="metric-value">{(metrics.requestsPerMin/1000).toFixed(1)}K</span>
                  <span className="metric-label">Requests/min</span>
                </div>
              </div>
              <div className="metric-card">
                <div className="metric-icon">💾</div>
                <div className="metric-info">
                  <span className="metric-value">{metrics.cpuUsage}%</span>
                  <span className="metric-label">CPU Usage</span>
                </div>
              </div>
              <div className="metric-card">
                <div className="metric-icon">🌐</div>
                <div className="metric-info">
                  <span className="metric-value">{metrics.activeInstances}</span>
                  <span className="metric-label">Active Instances</span>
                </div>
              </div>
            </div>
          </div>

          {/* Current Deployment */}
          {currentDeployment && (
            <div className="deployment-monitor">
              <h3>🔄 Live Deployment</h3>
              <div className="deployment-progress">
                <div className="progress-header">
                  <span className="deployment-id">#{currentDeployment.substring(0, 8)}</span>
                  <div className="status-pill" style={{
                    backgroundColor: getStatusColor(
                      deployments.find(d => d.deploymentId === currentDeployment)?.status
                    )
                  }}>
                    {deployments.find(d => d.deploymentId === currentDeployment)?.status || 'STARTING'}
                  </div>
                </div>
                <div className="progress-message">
                  {deployments.find(d => d.deploymentId === currentDeployment)?.message || 'Initializing deployment...'}
                </div>
                <div className="progress-bar">
                  <div className="progress-fill" style={{ width: deploying ? '60%' : '100%' }}></div>
                </div>
              </div>
            </div>
          )}

          {/* Advanced Deployment History */}
          <div className="deployments-section">
            <div className="section-header">
              <h3>📋 Deployment History</h3>
              <div className="filters">
                <select className="filter-select">
                  <option>All Status</option>
                  <option>Completed</option>
                  <option>Failed</option>
                </select>
                <select className="filter-select">
                  <option>Last 7 days</option>
                  <option>Last 30 days</option>
                </select>
              </div>
            </div>
            
            <div className="deployments-table">
              <div className="table-header">
                <span>Repository</span>
                <span>Status</span>
                <span>Duration</span>
                <span>URL</span>
                <span>Actions</span>
              </div>
              {deployments.map(deployment => (
                <div key={deployment.deploymentId} className="table-row">
                  <div className="repo-info">
                    <div className="repo-name">{deployment.githubUrl?.split('/').pop() || 'Unknown'}</div>
                    <div className="repo-url">{deployment.githubUrl}</div>
                  </div>
                  <div className="status-cell">
                    <div className="status-badge" style={{ backgroundColor: getStatusColor(deployment.status) }}>
                      {deployment.status}
                    </div>
                  </div>
                  <div className="duration-cell">
                    {deployment.endTime && deployment.startTime ? 
                      Math.round((new Date(deployment.endTime) - new Date(deployment.startTime)) / 1000) + 's' : 
                      'In progress'
                    }
                  </div>
                  <div className="url-cell">
                    {deployment.applicationUrl && (
                      <a href={deployment.applicationUrl} target="_blank" rel="noopener noreferrer" className="url-link">
                        🚀 Live App
                      </a>
                    )}
                  </div>
                  <div className="actions-cell">
                    <button className="action-btn">📊 Logs</button>
                    <button className="action-btn">🔄 Redeploy</button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Security & Monitoring Dashboard */}
          <div className="monitoring-grid">
            <div className="monitor-card">
              <h4>🔒 Security Status</h4>
              <div className="security-items">
                <div className="security-item">
                  <span className={`security-icon ${securityStatus.sslEnabled ? 'green' : 'red'}`}>
                    {securityStatus.sslEnabled ? '✅' : '❌'}
                  </span>
                  <span>SSL/TLS Enabled</span>
                </div>
                <div className="security-item">
                  <span className={`security-icon ${securityStatus.wafProtection ? 'green' : 'red'}`}>
                    {securityStatus.wafProtection ? '✅' : '❌'}
                  </span>
                  <span>WAF Protection</span>
                </div>
                <div className="security-item">
                  <span className={`security-icon ${securityStatus.ddosShield ? 'green' : 'red'}`}>
                    {securityStatus.ddosShield ? '✅' : '❌'}
                  </span>
                  <span>DDoS Shield</span>
                </div>
                <div className="security-item">
                  <span className={`security-icon ${securityStatus.vulnerabilityScan ? 'green' : 'yellow'}`}>
                    {securityStatus.vulnerabilityScan ? '✅' : '⚠️'}
                  </span>
                  <span>Vulnerability Scan</span>
                </div>
              </div>
            </div>

            <div className="monitor-card">
              <h4>📊 Performance</h4>
              <div className="perf-chart">
                <div className="chart-bar" style={{ height: '60%' }}></div>
                <div className="chart-bar" style={{ height: '80%' }}></div>
                <div className="chart-bar" style={{ height: '45%' }}></div>
                <div className="chart-bar" style={{ height: '90%' }}></div>
                <div className="chart-bar" style={{ height: '70%' }}></div>
              </div>
            </div>

            <div className="monitor-card">
              <h4>💰 Cost Analysis</h4>
              <div className="cost-info">
                <div className="cost-item">
                  <span>This Month</span>
                  <span className="cost-value">${metrics.monthlyCost}</span>
                </div>
                <div className="cost-item">
                  <span>Projected</span>
                  <span className="cost-value">${metrics.projectedCost}</span>
                </div>
                <div className="cost-trend">📈 +{Math.round(((metrics.projectedCost - metrics.monthlyCost) / metrics.monthlyCost) * 100)}% vs last month</div>
              </div>
            </div>

            <div className="monitor-card">
              <h4>🚨 Alerts</h4>
              <div className="alerts-list">
                {alerts.map((alert, index) => (
                  <div key={index} className={`alert-item ${alert.type}`}>
                    <span>{alert.type === 'warning' ? '⚠️' : 'ℹ️'} {alert.message}</span>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  )
}

export default App
