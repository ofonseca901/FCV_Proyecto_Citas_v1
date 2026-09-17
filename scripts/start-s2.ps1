$ErrorActionPreference = 'Stop'
$projectRoot = Split-Path -Parent $PSScriptRoot
$secretFile = Join-Path $projectRoot '.env.s2'
if (-not (Test-Path -LiteralPath $secretFile)) {
    $random = [System.Security.Cryptography.RandomNumberGenerator]::Create()
    try {
        $accessBytes = New-Object byte[] 48
        $refreshBytes = New-Object byte[] 48
        $random.GetBytes($accessBytes)
        $random.GetBytes($refreshBytes)
        $lines = @(
            'S2_JWT_ACCESS_SECRET=' + [Convert]::ToBase64String($accessBytes)
            'S2_JWT_REFRESH_SECRET=' + [Convert]::ToBase64String($refreshBytes)
        )
        [IO.File]::WriteAllLines($secretFile, $lines, (New-Object Text.UTF8Encoding($false)))
    } finally { $random.Dispose() }
    Write-Host 'Secretos JWT locales creados en .env.s2 (ignorado por Git).'
}
Push-Location $projectRoot
try {
    docker compose --env-file .env --env-file .env.s2 -f docker-compose.yml -f docker-compose.s2.yml up -d
    if ($LASTEXITCODE -ne 0) { throw 'No se pudo iniciar S2.' }
    Write-Host 'Frontend: http://localhost:5173 | API: http://localhost:8080/actuator/health'
} finally { Pop-Location }
