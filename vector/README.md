#  Mainframe Modern Incident Synthesizer  
[GitHub Repository](https://github.gwd.broadcom.net/MFD/Vector.git)

[Blog](https://sites.google.com/d/1-h4fRDGot5FIjXF3947UVxfHfTPrALcw/p/1-TU0vlNFXTte5asp_MgoXmzIsUM2pb5W/edit)

 
Modern Incident Synthesizer is a modern way of resolving issues using Gen-AI Power. 

### Prerequisite
Python 3.11, Podman or Docker to host database

### Environment Setup  
Once you checkout the project run 
>pipenv shell

>pipenv install 

Install Podman container, and 
download pgvector image (created on top of Postgress version 15) and run the container

>podman run  -d  -p 0.0.0.0:5434:5432 -e POSTGRES_PASSWORD=password  --name pgvector docker.io/ankane/pgvector

Create the database, activate the `pgvector` extension and rollout DB schema [schema.sql](sql/schema.sql)
Then to prepare indexed data (vectorized data references), run below command only once 
>python embed-documents.py
  
   
### How to Run Mainframe Modern Incident Synthesizer Application
Run below batch command 
>run-main-app.bat

or 

>streamlit run  frontend.py


It will automatically open below URL address on browser  
>http://localhost:8501/ 



To play with ChatGPT for asking any question run below  command 
>python ask-anything.py

And browse it with below address   
>http://localhost:8000/ask_anything/playground/
