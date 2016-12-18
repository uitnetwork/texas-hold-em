package com.uitnetwork;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by ninhdoan on 12/17/16.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMockitoTests {

   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);
   }
}
