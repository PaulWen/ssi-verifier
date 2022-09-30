# Release Process

1. Update version in [pom.xml](./pom.xml)
1. Update app version in [Chart.yaml](./k8s/Chart.yaml)
2. Add a tag `X.X.X`
3. The [GitHub Action](./.github/workflows/docker-publish.yml) will
   automatically build and publish a new docker image
