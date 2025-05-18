 package br.com.spacegtech.business;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import static org.mockito.BDDMockito.*;

public class ListBDDTest {

    @Test
    void testMockingList_when_SizeIsCalled_ShouldReturn10() {
        List<?> list = mock(List.class);
        given(list.size()).willReturn(10);

        assertThat(list.size(), is(10));

    }

    @Test
    void testMockingList_when_SizeIsCalled_ShouldReturnMultipleValue() {
        List<?> list = mock(List.class);
        given(list.size()).willReturn(10).willReturn(20);

        assertThat(list.size(), is(10));
        assertThat(list.size(), is(20));
        assertThat(list.size(), is(20));
    }

    @Test
    void testMockingList_whenGetIsCalled_shouldReturnErudioAndNull() {
        // Given (BDDMockito)
        List<String> list = mock(List.class);
        given(list.get(0)).willReturn("Erudio");
        given(list.get(1)).willReturn(null);

        // When
        String resultIndex0 = list.get(0);
        String resultIndex1 = list.get(1);

        // Then (Hamcrest)
        assertThat(resultIndex0, is("Erudio"));
        assertThat(resultIndex1, is(nullValue()));
    }

    @Test
    void testMockingList_when_GetIsCalledWithArgumentMatcher_ShouldReturnErudio() {
        var list = mock(List.class);
        given(list.get(anyInt())).willReturn("Erudio");

        assertThat(list.get(0), is("Erudio"));
        assertThat(list.get(anyInt()), is("Erudio"));

    }

    @Test
    void testMockingList_when_GetIsCalledWithArgumentMatcher_ThrowsException() {
        var list = mock(List.class);
        given(list.get(anyInt())).willThrow(new RuntimeException("Foo Bar"));
        // When
        Throwable thrown = null;
        try {
            list.get(1); // Poderia ser qualquer índice
        } catch (RuntimeException e) {
            thrown = e;
        }
        assertThat(thrown, instanceOf(RuntimeException.class));
        assertThat(thrown.getMessage(), is("Foo Bar"));
    }
}