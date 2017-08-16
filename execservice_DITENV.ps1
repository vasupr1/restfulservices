param ( $sshUser, $hostKey, $password, $port, $artifactPath, $TargetPath )
Write-Output 'Started executing script'

$killCommand = 'kill -9 $(/sbin/lsof  -t -i:' + $port + ') > /dev/null'

Write-Output 'Command : ' + $killCommand

$command='/sbin/lsof -i :'+ $port

#check port activity first
$procID = & 'E:\Software\putty\plink.exe' $sshUser -hostkey $hostKey -pw $password $command | Out-String;


 if ( ! $procID )
 
 {
  #start the application
$startCommand = "sh -c 'nohup java -jar " + $artifactPath + " --spring.config.location=" + $TargetPath + "/config/application.properties > /dev/null 2>&1 &'";
Write-Output "Starting Service" + $startCommand;
&'E:\Software\putty\plink.exe' -ssh $sshUser -hostkey $hostKey -pw $password  $startCommand

 }
 Else
 {

#kill the process first
Write-Output "Stop Service " + $killCommand;

&'E:\Software\putty\plink.exe' -ssh $sshUser -hostkey $hostKey -pw $password $killCommand


#restart the application
$startCommand = "sh -c 'nohup java -jar " + $artifactPath + " --spring.config.location=" + $TargetPath + "/config/application.properties > /dev/null 2>&1 &'";
Write-Output "Starting Service" + $startCommand;
&'E:\Software\putty\plink.exe' -ssh $sshUser -hostkey $hostKey -pw $password  $startCommand

 }