#!/usr/bin/env bash
kubectl apply -f ./test-ingres.yaml
sleep 3
kubectl delete ingressroute.traefik.containo.us --namespace=webapps-test  omega-ingres-test