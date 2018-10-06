Add-Type -AssemblyName System.IO.Compression.FileSystem;

# https://maven.apache.org/download.cgi
$mavenVersion = "3.5.4";
$currentDir = (Get-Item -Path ".").FullName;
$buildFile = Join-Path $currentDir -ChildPath "build.ps1";
$toolsDir = Join-Path $currentDir -ChildPath "tools";
$mavenFile = Join-Path $currentDir -ChildPath "tools" | Join-Path -ChildPath "maven-bin.zip";

if (!(Test-Path -Path $toolsDir )) {

	New-Item -Path $toolsDir -ItemType directory | Out-Null;
	
	Write-Host "downloading maven...";
	Invoke-WebRequest "http://www-eu.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip" -OutFile $mavenFile;

	Write-Host "extracting maven...";
	[System.IO.Compression.ZipFile]::ExtractToDirectory($mavenFile, $toolsDir);
}

if (!(Test-Path -Path $buildFile )) {
	Add-Content -Value "`$Env:JAVA_HOME=""C:\Program Files\Java\jdk10"";" -Path $buildFile;
	Add-Content -Value "`$Env:M2_HOME=""$toolsDir\apache-maven-$mavenVersion"";" -Path $buildFile;
	Add-Content -Value "`$Env:MAVEN_OPTS=""-Xmx1g -Dhttps.protocols=TLSv1.2"";" -Path $buildFile;
	Add-Content -Value "`$Env:JAVA_OPTS=""-Xmx1g"";" -Path $buildFile;
	
	Add-Content -Value "if (`$Env:Path | Select-String -NotMatch -SimpleMatch ""`$Env:M2_HOME"") {" -Path $buildFile;
	Add-Content -Value "  `$Env:Path += "";"" + `$Env:M2_HOME + ""\bin"";" -Path $buildFile;
	Add-Content -Value "}" -Path $buildFile;
	
	Add-Content -Value "if (`$Env:Path | Select-String -NotMatch -SimpleMatch ""`$Env:JAVA_HOME"") {" -Path $buildFile;
	Add-Content -Value "  `$Env:Path += "";"" + `$Env:JAVA_HOME + ""\bin"";" -Path $buildFile;
	Add-Content -Value "}" -Path $buildFile;
	
	Add-Content -Value "mvn -V clean package" -Path $buildFile;
}
