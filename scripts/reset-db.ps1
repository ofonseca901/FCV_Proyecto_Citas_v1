$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
Set-Location $Root
Write-Host "ATENCIÓN: se eliminará el volumen MySQL del laboratorio." -ForegroundColor Yellow
docker compose down -v
docker compose up -d mysql
& "$PSScriptRoot\db-smoke-test.ps1"
