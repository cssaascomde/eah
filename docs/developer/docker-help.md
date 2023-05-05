# Anleitung
Anleitung für das Erstellen von Docker-Images auf einem 
Mac mit ARM-Prozessor

## local

```
 docker build -t eah-tools .
```

## saascom

### Login
````
docker login https://docker.saascom.de
````

### Build and Push
````
 docker buildx build --push --platform=linux/amd64 -t docker.saascom.de/civento/eah-tools-amd64:1 .
 ````
