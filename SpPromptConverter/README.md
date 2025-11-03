# Agent Deployment at scale
Canada DevOps Community of Practice Hackathon Toronto - Team 6 

Project Name - Agent Deployment at scale

Team Mentor -

Participant Names - 

     Team Lead - Satyanaryana Velagapudi
     Team Members - Dipti Skills, Nithin James 

Docker Commands:
docker build -f Dockerfile -t agent-deployment:spPromptGenerator .
docker run -d -p 8080:8080 --name spPromptGenerator-agent agent-deployment:spPromptGenerator
