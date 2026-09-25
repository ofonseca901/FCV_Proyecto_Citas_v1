$ErrorActionPreference = 'Stop'
$seedPassword = 'Agenda123!'
$accounts = @()
1..10 | ForEach-Object { $accounts += @{ firstName="Usuario$_"; lastName='Demo'; documentType='CC'; documentNumber=("11000{0:D3}" -f $_); email=("usuario{0:D2}@agenda.local" -f $_); phone=("3001000{0:D3}" -f $_); password=$seedPassword } }
1..5 | ForEach-Object { $accounts += @{ firstName="Medico$_"; lastName='Demo'; documentType='CC'; documentNumber=("20000{0:D3}" -f $_); email=("medico{0:D2}@agenda.local" -f $_); phone=("3002000{0:D3}" -f $_); password=$seedPassword } }
$accounts += @{ firstName='Admin'; lastName='Demo'; documentType='CC'; documentNumber='90000001'; email='admin@agenda.local'; phone='3009000001'; password=$seedPassword }
foreach($account in $accounts) { try { Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/auth/register' -ContentType 'application/json' -Body ($account | ConvertTo-Json -Compress) | Out-Null } catch { if ($_.Exception.Response.StatusCode.value__ -ne 409) { throw } } }
Get-Content "$PSScriptRoot/../database/seed-demo.sql" | docker compose exec -T mysql sh -lc 'mysql -uroot -p"$MYSQL_ROOT_PASSWORD" "$MYSQL_DATABASE"'
Write-Output 'Seed sintético aplicado: 10 USER, 5 PROFESSIONAL y 1 ADMIN.'
