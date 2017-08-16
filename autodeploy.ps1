param ( $scpHost,$scpUser,$scpPassword,$hostkey,$restWarPath,$TargetPath )
Write-Host 'Started executing script'
Write-Host 'Host:' $scpHost
Write-Host 'User:' $scpUser
Start-Process -NoNewWindow -PassThru 'C:\putty\pscp.exe' -ArgumentList ("-scp -hostkey $hostkey -pw $scpPassword $restWarPath $($scpUser)@$($scpHost):$TargetPath")
