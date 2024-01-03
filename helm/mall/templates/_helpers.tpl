{{- define "mall.deployment" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .name }}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mall-member
  template:
    metadata:
      labels:
        app: mall-member
    spec:
      initContainers:
      - name: wait-for-nacos:{{ $.Values.version }}
        image: busybox
        command: ["sh", "-c", "until wget --spider -S http://nacos:8848/nacos/ 2>&1 | grep 'HTTP/.* 200'; do echo 'Waiting for Nacos to start...'; sleep 5; done;"]
      containers:
      - name: mall-member
        image: renchao05/mall-member:0.0.1
        ports:
        - containerPort: 8080
        env:
        - name: spring.profiles.active
          value: "dev"

---
apiVersion: v1
kind: Service
metadata:
  name: mall-member
spec:
  selector:
    app: mall-member
  ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
  type: NodePort

{{- end }}
