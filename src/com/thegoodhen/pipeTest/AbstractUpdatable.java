package com.thegoodhen.pipeTest;

public abstract class AbstractUpdatable {
private boolean obsolete=true;
	public final void requestUpdate(boolean forced)
	{
		if(!isObsolete() && !forced)
		{
			return;
		}
		this.obsolete=false;
		update();
	}
	
	
	public final void requestUpdate()
	{
		requestUpdate(false);
	}
	
	protected void update()
	{
		//update(false);
	}
	public boolean isObsolete()
	{
		return obsolete;
	}
	public void deprecate()
	{
		this.obsolete=true;
	}
	public String getRequestString()
	{
		return null;
	}

}
