# Ensure prefix is passed in
if ($args.Length -lt 1) {
    Write-Host "smoketest needs prefix"
    exit
}

$PREFIX = $args[0]

# Check if doing a local smoke test
if ($PREFIX -ne "local") {
    Write-Host "Remote Smoke Test in CF"
    $STD_APP_URL = $PREFIX
}
else {
    Write-Host "Local Smoke Test"
    $STD_APP_URL = "http://localhost:8080"
}

Write-Host "STD_APP_URL=$STD_APP_URL"

Write-Host "=== Criando pauta ==="
$RESPONSE = Invoke-RestMethod -Uri "$STD_APP_URL/v1/pauta" -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"titulo": "pauta"}'
$PAUTA_ID = $RESPONSE.id
Write-Host ""
Write-Host "PAUTA_ID=$PAUTA_ID"
Write-Host ""

Write-Host "=== Iniciando votação ==="
Invoke-RestMethod -Uri "$STD_APP_URL/v1/pauta/iniciar" -Method POST -Headers @{"Content-Type"="application/json"} -Body "{ ""minutos"": 1, ""id"": $PAUTA_ID }"
Write-Host ""
Start-Sleep -Seconds 2


Write-Host "=== Votando ==="
Invoke-RestMethod -Uri "$STD_APP_URL/v1/voto" -Method POST -Headers @{"Content-Type"="application/json"} -Body "{ ""id_pauta"": $PAUTA_ID, ""cpf"": ""810.072.500-46"", ""voto"": ""sim"" }"
Write-Host ""
Start-Sleep -Seconds 60


Write-Host "=== Obtendo resultado ==="
Invoke-RestMethod -Uri "$STD_APP_URL/v1/resultado/$PAUTA_ID" -Method POST
