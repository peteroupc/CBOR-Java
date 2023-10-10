package com.upokecenter.util;

  /**
   * Interface for random-number generators.
   */
  public interface IRandomGen
  {
    /**
     * Randomly generates a set of bytes.
     * @param bytes Byte buffer to store the random bytes.
     * @param offset A zero-based index showing where the desired portion of {@code
     * bytes} begins.
     * @param length The length, in bytes, of the desired portion of {@code bytes}
     * (but not more than {@code bytes} 's length).
     * @return Number of bytes returned.
     */
    int GetBytes(byte[] bytes, int offset, int length);
  }
