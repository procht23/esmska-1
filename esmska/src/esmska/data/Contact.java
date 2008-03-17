/*
 * Contact.java
 *
 * Created on 21. červenec 2007, 0:57
 */

package esmska.data;

import java.beans.*;
import java.text.Collator;

/** SMS Contact
 * @author ripper
 */
public class Contact extends Object implements Comparable<Contact> {
    
    private String name;
    /** full phone number including the country code (starting with "+") */
    private String number;
    private String operator;
    
    private PropertyChangeSupport propertySupport;
    
    public Contact() {
        this(null,null,null);
    }
    
    public Contact(String name, String number, String operator) {
        this.name = name;
        this.number = number;
        this.operator = operator;
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertySupport.firePropertyChange("name", oldName, name);
    }
    
    /**
     * Getter for property number.
     * @return phone number not including the country code
     */
    public String getNumber() {
        return this.number;
    }
    
    /**
     * Setter for property number.
     * @param number New value of property number.
     */
    public void setNumber(String number) {
        String oldNumber = this.number;
        this.number = number;
        propertySupport.firePropertyChange("number", oldNumber, number);
    }
    
    /**
     * Getter for property operator.
     * @return Value of property operator.
     */
    public String getOperator() {
        return this.operator;
    }
    
    /**
     * Setter for property operator.
     * @param operator New value of property operator.
     */
    public void setOperator(String operator) {
        String oldOperator = this.operator;
        this.operator = operator;
        propertySupport.firePropertyChange("operator", oldOperator, operator);
    }
    
    public int compareTo(Contact c) {
        int result = 0;
        Collator collator = Collator.getInstance();
        
        //name
        result = collator.compare(this.getName(), c.getName());
        if (result != 0)
            return result;
        //number
        result = collator.compare(this.getNumber(), c.getNumber());
        if (result != 0)
            return result;
        //operator
        if (this.getOperator() == null) {
            if (c.getOperator() != null)
                result = -1;
        } else {
            result = this.getOperator().toString().compareTo(c.getOperator().toString());
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        return getName();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Contact))
            return false;
        Contact c = (Contact) obj;
        
        return getName().equals(c.getName()) && getNumber().equals(c.getNumber()) 
                && getOperator().equals(c.getOperator());
    }
    
    @Override
    public int hashCode() {
        return (getName() == null ? 13 : getName().hashCode()) *
                (getNumber() == null ? 23 : getNumber().hashCode()) *
                (getOperator() == null ? 31 : getOperator().toString().hashCode());
    }
    
    
}
