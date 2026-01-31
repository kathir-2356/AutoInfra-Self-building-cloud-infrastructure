# 🚀 AutoInfra - Zero-Touch AWS Deployment Platform

Complete automated infrastructure deployment system with Terraform, Java Spring Boot, and React.

## 📁 Project Structure

```
autoinfra/
├── terraform/              # AWS Infrastructure
├── backend/               # Java Spring Boot
├── frontend/              # React Dashboard
└── scripts/               # Automation Scripts
```

## 🎯 Features

- **Auto Infrastructure**: EKS, RDS, Redis, ALB, CloudFront, S3
- **Auto Security**: WAF, Security Groups, IAM Roles, Encryption
- **Auto Monitoring**: CloudWatch, Logging, Metrics, Alerts

## 📋 Prerequisites

- Java 17+
- Node.js 18+
- Terraform 1.5+
- AWS CLI configured
- Maven

## 🚀 Quick Start

### Option 1: Local Development (No AWS)

```bash
deploy-all.bat
```

This will:
1. ✅ Install dependencies
2. ✅ Start backend (port 8080)
3. ✅ Start frontend (port 3001)

### Option 2: Deploy to AWS

**Step 1: Configure AWS credentials**
```bash
aws configure
```
Enter your AWS Access Key, Secret Key, and region (e.g., us-east-1)

**Step 2: Deploy to AWS**
```bash
deploy-aws.bat
```

This will:
1. ✅ Verify AWS credentials
2. ✅ Initialize Terraform
3. ✅ Deploy EC2, VPC, S3 to AWS
4. ✅ Show deployment outputs

**See [AWS-DEPLOYMENT-GUIDE.md](AWS-DEPLOYMENT-GUIDE.md) for detailed instructions**

### Manual Deployment:

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm run dev
```

**AWS Infrastructure:**
```bash
cd terraform
terraform init
terraform apply
```

## 🌐 Access

- **Dashboard**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Health Check**: http://localhost:8080/api/health

## 📚 API Endpoints

- `POST /api/deploy` - Deploy application
- `GET /api/deployment/{id}/status` - Get deployment status
- `GET /api/deployments` - List all deployments
- `GET /api/health` - Health check

## 🏗️ Infrastructure Components

- **VPC**: Multi-AZ with public/private subnets
- **EKS**: Kubernetes cluster with auto-scaling
- **RDS**: PostgreSQL database
- **ElastiCache**: Redis cluster
- **ALB**: Application Load Balancer
- **CloudFront**: CDN distribution
- **S3**: Storage buckets
- **WAF**: Web Application Firewall

## 🔒 Security

- Encrypted data at rest and in transit
- Security groups and network ACLs
- IAM roles with least privilege
- WAF protection against common attacks

## 📊 Monitoring

- CloudWatch metrics and logs
- Application performance monitoring
- Infrastructure health checks
- Automated alerts

## 🛠️ Development

### Backend (Java Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev
```

### Infrastructure (Terraform)

```bash
cd terraform
terraform init
terraform plan
terraform apply
```

## 📝 Configuration

### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/autoinfradb
spring.datasource.username=autoinfra
spring.datasource.password=ChangeMe123!
```

### Terraform Variables

Edit `terraform/variables.tf` or create `terraform.tfvars`:

```hcl
aws_region = "us-east-1"
project_name = "autoinfra-app"
environment = "production"
```

## 🧪 Testing

```bash
# Backend tests
cd backend
mvn test

# Frontend tests
cd frontend
npm test
```

## 📦 Build for Production

### Backend

```bash
cd backend
mvn clean package
java -jar target/autoinfra-backend-1.0.0.jar
```

### Frontend

```bash
cd frontend
npm run build
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

MIT License

## 🆘 Support

For issues and questions, please open an issue on GitHub.

## 🎉 Acknowledgments

Built with:
- Spring Boot
- React
- Terraform
- AWS SDK
- Vite
"# AutoInfra-Self-building-cloud-infrastructure" 
