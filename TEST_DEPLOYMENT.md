# Test Your Deployment

## Step 1: Deploy a Simple HTML Project

Use this test repo: `https://github.com/github/personal-website`

## Step 2: Check the URL

After deployment completes, you should get: `http://XX.XX.XX.XX`

## Step 3: Verify

Open the URL in browser. You should see:
- ✅ The actual website content
- ❌ NOT the GitHub repository page

## If you see GitHub repo page:

The EC2 instance isn't serving the project. Check:

1. **SSH into EC2:**
```bash
ssh -i your-key.pem ec2-user@XX.XX.XX.XX
```

2. **Check Apache:**
```bash
sudo systemctl status httpd
ls -la /var/www/html/
```

3. **Check logs:**
```bash
sudo tail -f /var/log/cloud-init-output.log
```

## Current Issue:

The deployment is creating EC2 but not properly:
1. Cloning the repo
2. Building the project
3. Serving it via Apache

**What URL are you getting when deployment completes?**
