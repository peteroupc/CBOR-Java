# com.upokecenter.cbor.CBORObject

    public final class CBORObject extends Object implements Comparable<CBORObject>

Represents an object in Concise Binary Object Representation (CBOR) and
 contains methods for reading and writing CBOR data. CBOR is defined
 in RFC 7049. <p><b>Converting CBOR objects</b></p> <p>There are many
 ways to get a CBOR object, including from bytes, objects, streams and
 JSON, as described below.</p> <p><b>To and from byte arrays:</b> The
 CBORObject.DecodeToBytes method converts a byte array in CBOR format
 to a CBOR object. The EncodeToBytes method converts a CBOR object to
 its corresponding byte array in CBOR format.</p> <p><b>To and from
 data streams:</b> The CBORObject.Write methods write many kinds of
 objects to a data stream, including numbers, CBOR objects, strings,
 and arrays of numbers and strings. The CBORObject.Read method reads a
 CBOR object from a data stream.</p> <p><b>To and from other
 objects:</b> The CBORObject.FromObject method converts many kinds of
 objects to a CBOR object, including numbers, strings, and arrays and
 maps of numbers and strings. Methods like AsDouble, AsByte, and
 AsString convert a CBOR object to different types of object.</p>
 <p><b>To and from JSON:</b> This class also doubles as a reader and
 writer of JavaScript Object Notation (JSON). The
 CBORObject.FromJSONString method converts JSON to a CBOR object, and
 the ToJSONString method converts a CBOR object to a JSON string.</p>
 <p>In addition, the CBORObject.WriteJSON method writes many kinds of
 objects as JSON to a data stream, including numbers, CBOR objects,
 strings, and arrays of numbers and strings. The CBORObject.Read
 method reads a CBOR object from a JSON data stream.</p>
 <p><b>Comparison Considerations:</b></p> <p>Instances of CBORObject
 should not be compared for equality using the "==" operator; it's
 possible to create two CBOR objects with the same value but not the
 same reference. (The "==" operator might only check if each side of
 the operator is the same instance.)</p> <p>This class's natural
 ordering (under the compareTo method) is not consistent with the
 Equals method. This means that two values that compare as equal under
 the compareTo method might not be equal under the Equals method. This
 is important to consider especially if an application wants to
 compare numbers, since the CBOR number type supports numbers of
 different formats, such as big integers, rational numbers, and
 arbitrary-precision decimal numbers.</p> <p>Another consideration is
 that two values that are otherwise equal may have different tags. To
 strip the tags from a CBOR object before comparing, use the
 <code>Untag</code> method.</p> <p>To compare two numbers, the
 CompareToIgnoreTags or compareTo method should be used. Which method
 to use depends on whether two equal values should still be considered
 equal if they have different tags.</p> <p>Although this class is
 inconsistent with the Equals method, it is safe to use CBORObject
 instances as hash keys as long as all of the keys are untagged text
 strings (which means GetTags returns an empty array and the Type
 property, or "getType()" in Java, returns TextString). This is
 because the natural ordering of these instances is consistent with
 the Equals method.</p> <p><b>Thread Safety:</b></p> <p>CBOR objects
 that are numbers, "simple values", and text strings are immutable
 (their values can't be changed), so they are inherently safe for use
 by multiple threads.</p> <p>CBOR objects that are arrays, maps, and
 byte strings are mutable, but this class doesn't attempt to
 synchronize reads and writes to those objects by multiple threads, so
 those objects are not thread safe without such synchronization.</p>
 <p>One kind of CBOR object is called a map, or a list of key-value
 pairs. Keys can be any kind of CBOR object, including numbers,
 strings, arrays, and maps. However, text strings are the most
 suitable to use as keys; other kinds of CBOR object are much better
 used as map values instead, keeping in mind that some of them are not
 thread safe without synchronizing reads and writes to them.</p> <p>To
 find the type of a CBOR object, call its Type property (or
 "getType()" in Java). The return value can be Number, Boolean,
 SimpleValue, or TextString for immutable CBOR objects, and Array,
 Map, or ByteString for mutable CBOR objects.</p> <p><b>Nesting
 Depth:</b></p> <p>The DecodeFromBytes and Read methods can only read
 objects with a limited maximum depth of arrays and maps nested within
 other arrays and maps. The code sets this maximum depth to 500
 (allowing more than enough nesting for most purposes), but it's
 possible that stack overflows in some runtimes might lower the
 effective maximum nesting depth. When the nesting depth goes above
 500, the DecodeFromBytes and Read methods throw a CBORException.</p>
 <p>The ReadJSON and FromJSONString methods currently have nesting
 depths of 1000.</p>
