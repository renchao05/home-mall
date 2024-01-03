{{- define "mid.cm" -}}
apiVersion: v1
kind: ConfigMap
metadata:
  name: {{ .name }}-cm
data:
{{- end }}



{{- define "mid.pv" -}}
apiVersion: v1
kind: PersistentVolume
metadata:
  name: {{ .name }}-pv
  labels:
    name: {{ .name }}
spec:
  hostPath:
    path: {{ .path }}
  accessModes: ["ReadWriteOnce"]
  capacity:
    storage: {{ .size }}
  persistentVolumeReclaimPolicy: Retain
{{- end }}



{{- define "mid.svc" -}}
apiVersion: v1
kind: Service
metadata:
  name: {{ .name }}
spec:
  {{- if .type }}
  type: {{ .type }}
  {{- end }}
  selector:
    app: {{ .selector }}
  ports:
    {{- range .ports }}
    - protocol: TCP
      port: {{ . }}
      name: {{ . }}-port
    {{- end }}
  {{- if .clusterIP }}
  clusterIP: {{ .clusterIP }}
  {{- end }}
  
{{- end }}



{{- define "mid.deploy" -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .name }}
spec:
  replicas: 1
  selector:
    matchLabels:
      app: {{ .name }}
  template:
    metadata:
      labels:
        app: {{ .name }}
    spec:
      {{- if .initContainers }}
      initContainers:
        {{- range .initContainers }}
        - name: {{ .name }}
          image: {{ .image }}
          command: ["sh","-c",{{ .command | quote }}]
        {{- end }}
      {{- end }}
      {{- template "mid.containers" . }}
{{- end }}



{{- define "mid.sts" -}}
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: {{ .name }}
spec:
  serviceName: {{ .name }}
  replicas: 1
  selector:
    matchLabels:
      app: {{ .name }}
  template:
    metadata:
      labels:
        app: {{ .name }}
    spec:
      {{- template "mid.containers" . }}
  {{- if .volumeClaimTemplates }}
  volumeClaimTemplates:
    {{- range .volumeClaimTemplates }}
    - metadata:
        name: {{ .name }}
      spec:
        accessModes: [ "ReadWriteOnce" ]
        resources:
          requests:
            storage: {{ .size }}
        selector:
          matchLabels:
            name: {{ .label }}
    {{- end }}
  {{- end }}
{{- end }}


{{- define "mid.containers" }}
      containers:
        - name: {{ .name }}
          image: {{ .image }}
          {{- if .command }}
          command:
            {{- range .command }}
            - {{ . }}
            {{- end }}
          {{- end }}
          {{- if .env }}
          env:
            {{- range .env }}
            - name: {{ .name }}
              value: {{ .value | quote }}
            {{- end }}
          {{- end }}
          {{- if .ports }}
          ports:
            {{- range .ports }}
            - containerPort: {{ . }}
            {{- end }}
          {{- end }}
          {{- if .volumeMounts }}
          volumeMounts:
            {{- range .volumeMounts }}
            - name: {{ .name }}
              mountPath: {{ .mountPath }}
            {{- end }}
          {{- end }}
      {{- if .volumes }}
      volumes:
        {{- range .volumes }}
        - name: {{ .name }}
          {{ .type }}: 
            {{ .typeNameKey }}: {{ .typeNameValue }}
        {{- end }}
      {{- end }}
{{- end }}

