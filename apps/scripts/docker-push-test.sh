#! bin/bash

docker build -t nanana0105/se-form-webapp:test ../SE-form-backend

docker push nanana0105/se-form-webapp:test
