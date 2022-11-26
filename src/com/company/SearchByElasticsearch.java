package com.company;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SearchByElasticsearch {

    RestHighLevelClient client;
    String indice;

    public SearchByElasticsearch(String indice) {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        this.indice = indice;
    }

    public ArrayList<String> search(String text) {
        ArrayList<String> data = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indice);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.from(0); //from which record start searching
        sourceBuilder.size(10); //how many records search

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("attachment.content", text)
                .fuzziness(Fuzziness.AUTO) //allows searching similar words / maximum transformations
                .prefixLength(3) //how many first letters cannot be change by transformation
                .maxExpansions(5); //number of maximum transformation


        sourceBuilder.query(matchQueryBuilder);

        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            analyzeData(searchResponse, data);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("[SearchByElasticSearch]Data search failed...");
        }

        return data;
    }

    private void analyzeData(SearchResponse searchResponse, ArrayList<String> dane) {
        SearchHits hits = searchResponse.getHits();

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {

            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String documentTitle = (String) sourceAsMap.get("title");
            String documentAuthor = (String) sourceAsMap.get("authorOfBook");

            dane.add(documentAuthor);
            dane.add(documentTitle);

            System.out.println("Title: " + documentTitle);
            System.out.println("Author: " + documentAuthor);
        }
    }

}
