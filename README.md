# Order service
```
helm install polardb_order bitnami/postgresql --set postgresqlUsername=admin --set postgresqlPassword=admin --set postgresqlDatabase=polardb_order --set image.tag=13 --set service.port=5433
```
