package de.hallo.abstractlayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Shell {

    public CommandOutput executeCommand(String command) throws CommandExecutionException {
        return this.executeCommand(new String[] {command});
    }

    public CommandOutput executeRootCommand(String command) throws CommandExecutionException {
        return this.executeCommand(new String[] {"su", "-c",command});
    }

    protected CommandOutput executeCommand(String[] command) throws CommandExecutionException{
        CommandOutput output = new CommandOutput();
        try {
            Process process = new ProcessBuilder(command).start();
            BufferedReader STDOUT = new BufferedReader( new InputStreamReader(process.getInputStream()));
            BufferedReader STDERR = new BufferedReader( new InputStreamReader(process.getErrorStream()));
            try {
                process.waitFor();
                output.exitCode = process.exitValue();
            } catch (InterruptedException e) {
                throw new CommandExecutionException(e.toString());
            }
            output.output = readBufferedReaderToString(STDOUT);
            output.error = readBufferedReaderToString(STDERR);
        } catch (IOException e) {
            throw new CommandExecutionException(e.toString());
        }
        return output;
    }

    private String readBufferedReaderToString(BufferedReader reader) throws IOException {
        String output = "";
        String line;
        while ((line = reader.readLine()) != null) {
            output = output + "\n" + line;
        }
        return output;
    }
}
