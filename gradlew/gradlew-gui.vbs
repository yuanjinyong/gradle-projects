Set fileSystemObj = CreateObject("Scripting.FileSystemObject")
Set curFile = fileSystemObj.GetFile(Wscript.ScriptFullName)
logFileName = curFile.Path & ".log"
batFileName = curFile.ParentFolder.Path & "\g.bat --gui"
cmd = batFileName & ">" & logFileName & " 2>&1"
'MsgBox cmd
wscript.createObject("wscript.shell").run cmd, 0