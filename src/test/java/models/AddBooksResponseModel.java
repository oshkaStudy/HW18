package models;

import lombok.Data;

import java.util.List;

@Data
public class AddBooksResponseModel {
    List<IsbnModel> books;
}
