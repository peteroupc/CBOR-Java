package com.upokecenter.cbor;

    /**
     * An interface for reading Unicode characters from a data source.
     */
  interface ICharacterInput {
    /**
     * Reads a Unicode character from a data source.
     * @return Either a Unicode code point (from 0-0xd7ff or from 0xe000 to
     * 0x10ffff), or the value -1 indicating the end of the source.
     */
    int ReadChar();

    /**
     * Reads a sequence of Unicode code points from a data source.
     * @param chars Output buffer.
     * @param index Index in the output buffer to start writing to.
     * @param length Maximum number of code points to write.
     * @return The number of Unicode code points read, or 0 if the end of the
     * source is reached.
     */
    int Read(int[] chars, int index, int length);
  }
