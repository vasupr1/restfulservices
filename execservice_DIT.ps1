param ( $sshUser, $hostKey, $password, $port, $artifactPath, $TargetPath )
Write-Output 'Started executing script'
Write-Output 'Command : ' + $command

$killCommand = 'kill -9 $(/sbin/lsof  -t -i:' + $port + ') > /dev/null'

#kill the process first
Write-Output "Stop Service " + $killCommand;

&'E:\Software\putty\plink.exe' -ssh $sshUser -hostkey $hostKey -pw $password $killCommand


#restart the application
$startCommand = "sh -c 'nohup java -jar " + $artifactPath + $TargetPath + "/config/application.properties > /dev/null 2>&1 &'";
Write-Output "Starting Service" + $startCommand;
&'E:\Software\putty\plink.exe' -ssh $sshUser -hostkey $hostKey -pw $password  $startCommand