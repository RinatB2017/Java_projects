
package org.onvif.ver10.media.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.onvif.ver10.schema.StreamSetup;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StreamSetup" type="{http://www.onvif.org/ver10/schema}StreamSetup"/>
 *         &lt;element name="ProfileToken" type="{http://www.onvif.org/ver10/schema}ReferenceToken"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "streamSetup",
    "profileToken"
})
@XmlRootElement(name = "GetStreamUri")
public class GetStreamUri {

    @XmlElement(name = "StreamSetup", required = true)
    protected StreamSetup streamSetup;
    @XmlElement(name = "ProfileToken", required = true)
    protected String profileToken;

    /**
     * Gets the value of the streamSetup property.
     * 
     * @return
     *     possible object is
     *     {@link StreamSetup }
     *     
     */
    public StreamSetup getStreamSetup() {
        return streamSetup;
    }

    /**
     * Sets the value of the streamSetup property.
     * 
     * @param value
     *     allowed object is
     *     {@link StreamSetup }
     *     
     */
    public void setStreamSetup(StreamSetup value) {
        this.streamSetup = value;
    }

    /**
     * Gets the value of the profileToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileToken() {
        return profileToken;
    }

    /**
     * Sets the value of the profileToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileToken(String value) {
        this.profileToken = value;
    }

}
