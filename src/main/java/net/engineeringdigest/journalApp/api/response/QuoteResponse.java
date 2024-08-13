package net.engineeringdigest.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class QuoteResponse{
    private int statusCode;
    @JsonProperty("data")
    private Data fullData;
    private String message;
    private boolean success;

    @Getter
    @Setter
    public static class Data{
        private int page;
        private int limit;
        private int totalPages;
        private boolean previousPage;
        private boolean nextPage;
        private int totalItems;
        private int currentPageItems;
        private List<Datum> data;

        @Getter
        @Setter
        public static class Datum{
            private String author;
            private String content;
            private List<String> tags;
            private String authorSlug;
            private int length;
            private String dateAdded;
            private String dateModified;
            private int id;
        }
    }
}




