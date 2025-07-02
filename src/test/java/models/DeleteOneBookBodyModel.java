package models;

import lombok.Data;

@Data
public class DeleteOneBookBodyModel {
    String isbn, userId;
}
