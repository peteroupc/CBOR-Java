package com.upokecenter.cbor;

  interface ICharacterInput
  {
    int ReadChar();

    int Read(int[] chars, int index, int length);
  }
