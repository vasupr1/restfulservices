param ( $scpHost,$scpUser,$scpPassword,$hostkey,$restWarPath,$TargetPath,$ServicePort,$RemoteArtifactPath )
Write-Host 'Started executing script'
Write-Host 'Host:' $scpHost
Write-Host 'User:' $scpUser
Start-Process -NoNewWindow -PassThru 'C:\putty\plink.exe' -ArgumentList ("-ssh -hostkey $hostkey $($scpUser)@$($scpHost) -pw $scpPassword /sbin/fuser -n tcp -k $ServicePort") -RedirectStandardError log.txt | Out-Null
Start-Process -NoNewWindow -PassThru 'C:\putty\plink.exe' -ArgumentList ("-ssh -hostkey $hostkey $($scpUser)@$($scpHost) -pw $scpPassword java -jar $RemoteArtifactPath")