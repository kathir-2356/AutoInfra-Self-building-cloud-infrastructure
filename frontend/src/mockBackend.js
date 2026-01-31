// Mock backend for demo without Java server
let deployments = []

export const mockBackend = {
  deploy: async (githubUrl) => {
    const deploymentId = Math.random().toString(36).substring(7)
    const deployment = {
      deploymentId,
      githubUrl,
      status: 'IN_PROGRESS',
      message: 'Analyzing repository...',
      startTime: new Date().toISOString()
    }
    
    deployments.push(deployment)
    
    // Simulate deployment progress
    setTimeout(() => {
      deployment.message = 'Generating Terraform configuration...'
    }, 2000)
    
    setTimeout(() => {
      deployment.message = 'Deploying AWS infrastructure...'
    }, 5000)
    
    setTimeout(() => {
      deployment.message = 'Configuring application...'
    }, 10000)
    
    setTimeout(() => {
      deployment.status = 'COMPLETED'
      deployment.message = 'Deployment completed successfully! (Demo Mode - Deploy to AWS for real URL)'
      deployment.applicationUrl = `http://localhost:3001 (Demo)`
      deployment.endTime = new Date().toISOString()
    }, 12000)
    
    return { deploymentId, status: 'STARTED', message: 'Deployment initiated successfully' }
  },
  
  getDeploymentStatus: async (id) => {
    return deployments.find(d => d.deploymentId === id) || null
  },
  
  getAllDeployments: async () => {
    return [...deployments].reverse()
  }
}
