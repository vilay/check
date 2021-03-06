/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Patrick Lam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */






package soot.jimple.internal;


import soot.tagkit.*;
import soot.util.*;
import java.util.*;
import soot.*;
import soot.jimple.*;
import soot.baf.*;
import soot.jimple.*;

public class JExitMonitorStmt extends AbstractStmt 
    implements ExitMonitorStmt
{
    ValueBox opBox;

    public JExitMonitorStmt(Value op)
    {
        this(Jimple.v().newImmediateBox(op));
    }

    protected JExitMonitorStmt(ValueBox opBox)
    {
        this.opBox = opBox;
    }

    public Object clone() 
    {
        return new JExitMonitorStmt(Jimple.cloneIfNecessary(getOp()));
    }

    public String toString()
    {
        return Jimple.v().EXITMONITOR + " "  + opBox.getValue().toString();
    }
    
    public void toString(UnitPrinter up) {
        up.literal(Jimple.v().EXITMONITOR);
        up.literal(" ");
        opBox.toString(up);
    }

    public Value getOp()
    {
        return (Value) opBox.getValue();
    }

    public void setOp(Value op)
    {
        opBox.setValue(op);
    }

    public ValueBox getOpBox()
    {
        return opBox;
    }

    public List getUseBoxes()
    {
        List list = new ArrayList();

        list.addAll(opBox.getValue().getUseBoxes());
        list.add(opBox);
    
        return list;
    }

    public void apply(Switch sw)
    {
        ((StmtSwitch) sw).caseExitMonitorStmt(this);

    }    

  public void convertToBaf(JimpleToBafContext context, List out)
  {
    ((ConvertToBaf)(getOp())).convertToBaf(context, out);

    Unit u;
    out.add(u = Baf.v().newExitMonitorInst());

    Unit currentUnit = this;

    Iterator it = currentUnit.getTags().iterator();	
    while(it.hasNext()) {
	u.addTag((Tag) it.next());
    }

  }


    
    public boolean fallsThrough(){return true;}
    public boolean branches(){return false;}        
    


}
