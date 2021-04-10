# Order service
```
helm install polardb-order bitnami/postgresql --set postgresqlUsername=admin --set postgresqlPassword=admin --set postgresqlDatabase=polardb-order --set image.tag=13 --set service.port=5432
```
