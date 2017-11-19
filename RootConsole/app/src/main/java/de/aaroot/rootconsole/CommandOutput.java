package de.hallo.abstractlayout;

public class CommandOutput
{
	public String output;
    public String error;
    public int exitCode;

    public String toString(){
        return output + error;
    }
}
