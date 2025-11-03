{{- define "myapp.name" -}}
{{- default .Chart.Name .Values.nameOverride -}}
{{- end -}}

{{- define "myapp.fullname" -}}
{{- printf "%s-%s" (include "myapp.name" .) .Release.Namespace | lower | trunc 63 | trimSuffix "-" -}}
{{- end -}}