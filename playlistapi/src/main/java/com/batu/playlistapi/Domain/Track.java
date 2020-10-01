package com.batu.playlistapi.Domain;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    /*
    track objesi içerisinde: "name" , "length"(string) , artist(string)
     */

    private String name;
    private String length;
    private String artist;

}
