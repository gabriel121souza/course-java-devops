package br.com.spacegtech.business;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListTest {

    @Test
    void testMockingList_when_SizeIsCalled_ShouldReturn10(){
        List<?> list = mock(List.class);
        when(list.size()).thenReturn(10);

        assertEquals(10, list.size());

    }
    @Test
    void testMockingList_when_SizeIsCalled_ShouldReturnMultipleValue(){
        List<?> list = mock(List.class);
        when(list.size()).thenReturn(10).thenReturn(20);

        assertEquals(10, list.size());
        assertEquals(20, list.size());
        assertEquals(20, list.size());

    }

    @Test
    void testMockingList_when_GetIsCalled_ShouldReturnErudio(){
        var list = mock(List.class);
        when(list.get(0)).thenReturn("Erudio");

        assertEquals("Erudio", list.get(0));
        assertNull( list.get(1));

    }

    @Test
    void testMockingList_when_GetIsCalledWithArgumentMatcher_ShouldReturnErudio(){
        var list = mock(List.class);
        when(list.get(anyInt())).thenReturn("Erudio");

        assertEquals("Erudio", list.get(0));
        assertEquals("Erudio" ,list.get(anyInt()));

    }
    @Test
    void testMockingList_when_GetIsCalledWithArgumentMatcher_ThrowsException(){
        var list = mock(List.class);
        when(list.get(anyInt())).thenThrow(new RuntimeException("Foo Bar"));

        assertThrows(RuntimeException.class,
                () -> {
            list.get(anyInt());
            }, ()-> "Should have throw an RuntimeException");

    }

}
