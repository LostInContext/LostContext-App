package com.lostincontext.data.playlist;


import android.os.Parcel;
import android.os.Parcelable;

import com.lostincontext.commons.images.DeezerImage;
import com.lostincontext.commons.images.DeezerImageUrlGenerator.DeezerImageType;

public class Playlist implements DeezerImage, Parcelable {

    private int id;
    private String title;
    private String creator;
    private String coverMd5;
    @DeezerImageType private int imageType;

    public Playlist() { }

    public Playlist(int id,
                    String title,
                    String creator,
                    String coverMd5,
                    @DeezerImageType int imageType) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.coverMd5 = coverMd5;
        this.imageType = imageType;
    }

    private Playlist(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.creator = in.readString();
        this.coverMd5 = in.readString();
        //noinspection WrongConstant
        this.imageType = in.readInt();
    }

    @Override public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creator='" + creator + '\'' +
                ", coverMd5='" + coverMd5 + '\'' +
                ", imageType=" + imageType +
                '}';
    }

    //region getters & setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String getCoverMd5() {
        return coverMd5;
    }

    public void setCoverMd5(String coverMd5) {
        this.coverMd5 = coverMd5;
    }

    @Override
    @DeezerImageType
    public int getImageType() {
        return imageType;
    }

    public void setImageType(@DeezerImageType int imageType) {
        this.imageType = imageType;
    }

    //endregion

    //region Parcelable

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(creator);
        out.writeString(coverMd5);
        out.writeInt(imageType);
    }

    public static final Parcelable.Creator<Playlist> CREATOR
            = new Parcelable.Creator<Playlist>() {
        public Playlist createFromParcel(Parcel in) {
            return new Playlist(in);
        }

        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    //endregion


    //region JSON
    public static String JSON = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 1687197553,\n" +
            "      \"title\": \"A Soundtrack for Coding\",\n" +
            "      \"duration\": 11537,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 37,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687197553\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687197553/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/5f60900990253e5ac45618d4afa80eca-da3c195d7680b627037b8baf3739646c-75a393078fcf0a40bf393bcbc824ef2e-80cd5d823a41ef71578761dc4ba5eeb1/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/5f60900990253e5ac45618d4afa80eca-da3c195d7680b627037b8baf3739646c-75a393078fcf0a40bf393bcbc824ef2e-80cd5d823a41ef71578761dc4ba5eeb1/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/5f60900990253e5ac45618d4afa80eca-da3c195d7680b627037b8baf3739646c-75a393078fcf0a40bf393bcbc824ef2e-80cd5d823a41ef71578761dc4ba5eeb1/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/5f60900990253e5ac45618d4afa80eca-da3c195d7680b627037b8baf3739646c-75a393078fcf0a40bf393bcbc824ef2e-80cd5d823a41ef71578761dc4ba5eeb1/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"2649426be58d06d54e4997d551a807c0\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687197553/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:42:10\",\n" +
            "      \"time_add\": 1458816131,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687198033,\n" +
            "      \"title\": \"Acoustic Spring\",\n" +
            "      \"duration\": 8531,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 38,\n" +
            "      \"fans\": 2,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687198033\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687198033/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/5c91ab35fe79409b86b3227ac1354745-23d6869f16f28e7daab6bf98588e6ff5-290ac5d9c73e8dd96d820c1a40175be9-fb4e582785f3067ba15e0be5f56eb59b/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/5c91ab35fe79409b86b3227ac1354745-23d6869f16f28e7daab6bf98588e6ff5-290ac5d9c73e8dd96d820c1a40175be9-fb4e582785f3067ba15e0be5f56eb59b/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/5c91ab35fe79409b86b3227ac1354745-23d6869f16f28e7daab6bf98588e6ff5-290ac5d9c73e8dd96d820c1a40175be9-fb4e582785f3067ba15e0be5f56eb59b/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/5c91ab35fe79409b86b3227ac1354745-23d6869f16f28e7daab6bf98588e6ff5-290ac5d9c73e8dd96d820c1a40175be9-fb4e582785f3067ba15e0be5f56eb59b/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"708bd5fa6cb49c0e61d66faa77ea0289\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687198033/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:42:30\",\n" +
            "      \"time_add\": 1458816150,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687199833,\n" +
            "      \"title\": \"Alt J\",\n" +
            "      \"duration\": 6631,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 31,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687199833\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687199833/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/0b6632badb79e00285a86cb00c062f14-50c7362187cacbe74c127ffbf6d251e5-835d761eeecbd18cccc87ffc331af8df-962c55b49c500b7e8f6cdfb8bbf687e4/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/0b6632badb79e00285a86cb00c062f14-50c7362187cacbe74c127ffbf6d251e5-835d761eeecbd18cccc87ffc331af8df-962c55b49c500b7e8f6cdfb8bbf687e4/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/0b6632badb79e00285a86cb00c062f14-50c7362187cacbe74c127ffbf6d251e5-835d761eeecbd18cccc87ffc331af8df-962c55b49c500b7e8f6cdfb8bbf687e4/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/0b6632badb79e00285a86cb00c062f14-50c7362187cacbe74c127ffbf6d251e5-835d761eeecbd18cccc87ffc331af8df-962c55b49c500b7e8f6cdfb8bbf687e4/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"758eb9a7afee87ebd124c40fddaa5327\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687199833/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:44:05\",\n" +
            "      \"time_add\": 1458816245,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687187613,\n" +
            "      \"title\": \"Annoying but couldn\\\\'t resist\",\n" +
            "      \"duration\": 10218,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 45,\n" +
            "      \"fans\": 3,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687187613\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687187613/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/d59ba2295d91292a8bcb372bad9d88be-875e28fbbb3a7b381ffd5839b5905445-30bc3d8c348ddddb00c44f28d3120ac5-ef4ba14238b9444aefd1307d95135862/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/d59ba2295d91292a8bcb372bad9d88be-875e28fbbb3a7b381ffd5839b5905445-30bc3d8c348ddddb00c44f28d3120ac5-ef4ba14238b9444aefd1307d95135862/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/d59ba2295d91292a8bcb372bad9d88be-875e28fbbb3a7b381ffd5839b5905445-30bc3d8c348ddddb00c44f28d3120ac5-ef4ba14238b9444aefd1307d95135862/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/d59ba2295d91292a8bcb372bad9d88be-875e28fbbb3a7b381ffd5839b5905445-30bc3d8c348ddddb00c44f28d3120ac5-ef4ba14238b9444aefd1307d95135862/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"bb1b986a17bd34805192a60d5a3b2271\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687187613/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:33:51\",\n" +
            "      \"time_add\": 1458896803,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687200593,\n" +
            "      \"title\": \"Blues\",\n" +
            "      \"duration\": 9752,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 36,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687200593\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687200593/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/7d5434fdb8555fac4a1a2d7117052f3d-9f75ac860337dab65e81c25fc2f11619-e7f45152ff7439f57131e57670bf5945-246d56dd8d3e6d2033bb5e997848cb06/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/7d5434fdb8555fac4a1a2d7117052f3d-9f75ac860337dab65e81c25fc2f11619-e7f45152ff7439f57131e57670bf5945-246d56dd8d3e6d2033bb5e997848cb06/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/7d5434fdb8555fac4a1a2d7117052f3d-9f75ac860337dab65e81c25fc2f11619-e7f45152ff7439f57131e57670bf5945-246d56dd8d3e6d2033bb5e997848cb06/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/7d5434fdb8555fac4a1a2d7117052f3d-9f75ac860337dab65e81c25fc2f11619-e7f45152ff7439f57131e57670bf5945-246d56dd8d3e6d2033bb5e997848cb06/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"c42dc284c182c06000a86d6ff17f6043\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687200593/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:44:40\",\n" +
            "      \"time_add\": 1458816280,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687188143,\n" +
            "      \"title\": \"Cardio\",\n" +
            "      \"duration\": 10296,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 49,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687188143\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687188143/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/2f14b2940ea2c9f92f0016a2840caafd-c7b47243191ac8dcf8c461ca66f7fc1c-ab49dab0ac75f65370456c1c18a97d5b-5b6570f1d260d76af29e1d5d76546728/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/2f14b2940ea2c9f92f0016a2840caafd-c7b47243191ac8dcf8c461ca66f7fc1c-ab49dab0ac75f65370456c1c18a97d5b-5b6570f1d260d76af29e1d5d76546728/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/2f14b2940ea2c9f92f0016a2840caafd-c7b47243191ac8dcf8c461ca66f7fc1c-ab49dab0ac75f65370456c1c18a97d5b-5b6570f1d260d76af29e1d5d76546728/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/2f14b2940ea2c9f92f0016a2840caafd-c7b47243191ac8dcf8c461ca66f7fc1c-ab49dab0ac75f65370456c1c18a97d5b-5b6570f1d260d76af29e1d5d76546728/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"e4039e9ffed9ef0b5228123424cdd023\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687188143/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:34:18\",\n" +
            "      \"time_add\": 1458815658,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687199223,\n" +
            "      \"title\": \"Couch Surfing\",\n" +
            "      \"duration\": 16890,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 74,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687199223\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687199223/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/cd5a8ba831f0dd8266b6151481611107-50fc7ee48f5c09e428320939b724e132-a4a480962573aad8567d60e81e2c4da2-368a4d823bcd76ee43bf211e73c6a1d7/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/cd5a8ba831f0dd8266b6151481611107-50fc7ee48f5c09e428320939b724e132-a4a480962573aad8567d60e81e2c4da2-368a4d823bcd76ee43bf211e73c6a1d7/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/cd5a8ba831f0dd8266b6151481611107-50fc7ee48f5c09e428320939b724e132-a4a480962573aad8567d60e81e2c4da2-368a4d823bcd76ee43bf211e73c6a1d7/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/cd5a8ba831f0dd8266b6151481611107-50fc7ee48f5c09e428320939b724e132-a4a480962573aad8567d60e81e2c4da2-368a4d823bcd76ee43bf211e73c6a1d7/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"296b0f4169e1d563157020bf2efe47ea\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687199223/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:43:33\",\n" +
            "      \"time_add\": 1458816214,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687187963,\n" +
            "      \"title\": \"Creativity Boost\",\n" +
            "      \"duration\": 10168,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 45,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687187963\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687187963/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/a7ba2af4d962061fa240147225bcd68c-a4937dfbc4fd74952ff91377d6c787a5-30d999c0b84eaf6f9dee7bd3675dbadf-b0eb0ebfb15439b8cdf923472db9a308/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/a7ba2af4d962061fa240147225bcd68c-a4937dfbc4fd74952ff91377d6c787a5-30d999c0b84eaf6f9dee7bd3675dbadf-b0eb0ebfb15439b8cdf923472db9a308/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/a7ba2af4d962061fa240147225bcd68c-a4937dfbc4fd74952ff91377d6c787a5-30d999c0b84eaf6f9dee7bd3675dbadf-b0eb0ebfb15439b8cdf923472db9a308/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/a7ba2af4d962061fa240147225bcd68c-a4937dfbc4fd74952ff91377d6c787a5-30d999c0b84eaf6f9dee7bd3675dbadf-b0eb0ebfb15439b8cdf923472db9a308/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"e46a2b1f6baeb29fe7e0dfa2016737ae\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687187963/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:34:07\",\n" +
            "      \"time_add\": 1458815647,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687199863,\n" +
            "      \"title\": \"dubstep\",\n" +
            "      \"duration\": 3343,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 14,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687199863\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687199863/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/07f77e2b833a9e31a852ca89cab041bc/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/07f77e2b833a9e31a852ca89cab041bc/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/07f77e2b833a9e31a852ca89cab041bc/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/07f77e2b833a9e31a852ca89cab041bc/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"a2eb17e59da90f300a3fb6dc54254367\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687199863/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:44:06\",\n" +
            "      \"time_add\": 1458816246,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687198823,\n" +
            "      \"title\": \"Feel Good Indie Rock\",\n" +
            "      \"duration\": 59216,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 278,\n" +
            "      \"fans\": 2,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687198823\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687198823/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/707712431cc0a0579c90aeda4d7cb04a-b6b42019c08aa0b67d531f9fc04af02b-c129e8ebfc9e6ddfeda6647b93f48857-7827f59d6e5df2e5c41f6651e83c786a/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/707712431cc0a0579c90aeda4d7cb04a-b6b42019c08aa0b67d531f9fc04af02b-c129e8ebfc9e6ddfeda6647b93f48857-7827f59d6e5df2e5c41f6651e83c786a/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/707712431cc0a0579c90aeda4d7cb04a-b6b42019c08aa0b67d531f9fc04af02b-c129e8ebfc9e6ddfeda6647b93f48857-7827f59d6e5df2e5c41f6651e83c786a/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/707712431cc0a0579c90aeda4d7cb04a-b6b42019c08aa0b67d531f9fc04af02b-c129e8ebfc9e6ddfeda6647b93f48857-7827f59d6e5df2e5c41f6651e83c786a/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"049dba00b5f6b5378a9336d9cc23ef96\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687198823/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:43:12\",\n" +
            "      \"time_add\": 1458816194,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687187873,\n" +
            "      \"title\": \"French Indie Pop\",\n" +
            "      \"duration\": 11093,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 50,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687187873\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687187873/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/360b14bc8dc057bcb1ea7b193fa2e679-c54d9ed188a910e7ff6f0fc58586ff7a-e056d108a484c0f6f919a8531750b74b-425271752d804f240699ad84da13bc3e/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/360b14bc8dc057bcb1ea7b193fa2e679-c54d9ed188a910e7ff6f0fc58586ff7a-e056d108a484c0f6f919a8531750b74b-425271752d804f240699ad84da13bc3e/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/360b14bc8dc057bcb1ea7b193fa2e679-c54d9ed188a910e7ff6f0fc58586ff7a-e056d108a484c0f6f919a8531750b74b-425271752d804f240699ad84da13bc3e/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/360b14bc8dc057bcb1ea7b193fa2e679-c54d9ed188a910e7ff6f0fc58586ff7a-e056d108a484c0f6f919a8531750b74b-425271752d804f240699ad84da13bc3e/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"a8ef0add507af0ed6b8153a142c9a1e5\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687187873/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:34:02\",\n" +
            "      \"time_add\": 1458815642,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687188683,\n" +
            "      \"title\": \"GAMING TIME\",\n" +
            "      \"duration\": 54092,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 207,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687188683\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687188683/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/15eddcb1c8a6295547105bca5d42ecb2-ce52b21f27910d5e0d4a33e162682979-6f2e21fc97d9235e61dce67a37e62d7e-48fe16fd865925dba8036a24ca1fc247/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/15eddcb1c8a6295547105bca5d42ecb2-ce52b21f27910d5e0d4a33e162682979-6f2e21fc97d9235e61dce67a37e62d7e-48fe16fd865925dba8036a24ca1fc247/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/15eddcb1c8a6295547105bca5d42ecb2-ce52b21f27910d5e0d4a33e162682979-6f2e21fc97d9235e61dce67a37e62d7e-48fe16fd865925dba8036a24ca1fc247/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/15eddcb1c8a6295547105bca5d42ecb2-ce52b21f27910d5e0d4a33e162682979-6f2e21fc97d9235e61dce67a37e62d7e-48fe16fd865925dba8036a24ca1fc247/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"4bca4e794fc7e6f9e29e6c232e05f5f6\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687188683/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:34:41\",\n" +
            "      \"time_add\": 1458815683,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1687187973,\n" +
            "      \"title\": \"good music\",\n" +
            "      \"duration\": 5906,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 25,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1687187973\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1687187973/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/bca07a0bfaaf112657e078b3cbc48d82-b268d9022d1b8562a31c240e0156c71c-e45246b90badf4bd3a9a994729433bf8-ec31e90c0a7bf5f06db5de268d163135/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/bca07a0bfaaf112657e078b3cbc48d82-b268d9022d1b8562a31c240e0156c71c-e45246b90badf4bd3a9a994729433bf8-ec31e90c0a7bf5f06db5de268d163135/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/bca07a0bfaaf112657e078b3cbc48d82-b268d9022d1b8562a31c240e0156c71c-e45246b90badf4bd3a9a994729433bf8-ec31e90c0a7bf5f06db5de268d163135/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/bca07a0bfaaf112657e078b3cbc48d82-b268d9022d1b8562a31c240e0156c71c-e45246b90badf4bd3a9a994729433bf8-ec31e90c0a7bf5f06db5de268d163135/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"84683d6305d38c2aadf3fccd09bd5466\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1687187973/tracks\",\n" +
            "      \"creation_date\": \"2016-03-24 10:34:08\",\n" +
            "      \"time_add\": 1458815649,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 21292726,\n" +
            "        \"name\": \"angie23\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/21292726/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1447190005,\n" +
            "      \"title\": \"droidcon\",\n" +
            "      \"duration\": 3105,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 9,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1447190005\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1447190005/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/b8b38d2483204b790db10a7b258fbef1-b28987b81f0c5e24ecdbc9093fe14d3e-53d576a5c4f8c33b091c99dd8327bb68-862fd3783290bde0cd5662f643644242/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/b8b38d2483204b790db10a7b258fbef1-b28987b81f0c5e24ecdbc9093fe14d3e-53d576a5c4f8c33b091c99dd8327bb68-862fd3783290bde0cd5662f643644242/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/b8b38d2483204b790db10a7b258fbef1-b28987b81f0c5e24ecdbc9093fe14d3e-53d576a5c4f8c33b091c99dd8327bb68-862fd3783290bde0cd5662f643644242/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/b8b38d2483204b790db10a7b258fbef1-b28987b81f0c5e24ecdbc9093fe14d3e-53d576a5c4f8c33b091c99dd8327bb68-862fd3783290bde0cd5662f643644242/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"e85b242f8c8c34f3f9ebe39b7a2f2adb\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1447190005/tracks\",\n" +
            "      \"creation_date\": \"2015-11-08 22:32:04\",\n" +
            "      \"time_add\": 1465465077,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 919930145,\n" +
            "      \"title\": \"HackTest\",\n" +
            "      \"duration\": 2432,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 10,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/919930145\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/919930145/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/3cc228ca0b98ebfc25d813d7c6ab8fbe-f157c831bd594b94a14b0072bfd0003c-90c444b7be42b61b2af91feadf4174ad-b676dae4b8a3fe97fa764c4de7e32ac1/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/3cc228ca0b98ebfc25d813d7c6ab8fbe-f157c831bd594b94a14b0072bfd0003c-90c444b7be42b61b2af91feadf4174ad-b676dae4b8a3fe97fa764c4de7e32ac1/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/3cc228ca0b98ebfc25d813d7c6ab8fbe-f157c831bd594b94a14b0072bfd0003c-90c444b7be42b61b2af91feadf4174ad-b676dae4b8a3fe97fa764c4de7e32ac1/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/3cc228ca0b98ebfc25d813d7c6ab8fbe-f157c831bd594b94a14b0072bfd0003c-90c444b7be42b61b2af91feadf4174ad-b676dae4b8a3fe97fa764c4de7e32ac1/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"cc59a88e483a05888d0d2c2393b66eff\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/919930145/tracks\",\n" +
            "      \"creation_date\": \"2014-07-04 13:11:21\",\n" +
            "      \"time_add\": 1410955215,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 302720941,\n" +
            "      \"title\": \"Loved tracks\",\n" +
            "      \"duration\": 29953,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": true,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 124,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/302720941\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/302720941/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/playlist/a3c5956c5fafaeb8c0356c7e88f79520/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/playlist/a3c5956c5fafaeb8c0356c7e88f79520/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/playlist/a3c5956c5fafaeb8c0356c7e88f79520/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/playlist/a3c5956c5fafaeb8c0356c7e88f79520/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"ffdc629092179a92ee6490c5fc8c6bad\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/302720941/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1467981147,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 598842585,\n" +
            "      \"title\": \"test\",\n" +
            "      \"duration\": 1434,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 9,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/598842585\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/598842585/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/8e386117151a0fb987e28428ded91879-c3787111901e5b899f96e403d1c91ec5-585e3245a27713e8ccc8f758d4f06c0a-8c3afebb68e7cb4865abf163d6e28dab/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/8e386117151a0fb987e28428ded91879-c3787111901e5b899f96e403d1c91ec5-585e3245a27713e8ccc8f758d4f06c0a-8c3afebb68e7cb4865abf163d6e28dab/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/8e386117151a0fb987e28428ded91879-c3787111901e5b899f96e403d1c91ec5-585e3245a27713e8ccc8f758d4f06c0a-8c3afebb68e7cb4865abf163d6e28dab/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/8e386117151a0fb987e28428ded91879-c3787111901e5b899f96e403d1c91ec5-585e3245a27713e8ccc8f758d4f06c0a-8c3afebb68e7cb4865abf163d6e28dab/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"fd7aa1c8e9025b7f62a3a90656010bc1\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/598842585/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1418321952,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1124639141,\n" +
            "      \"title\": \"test\",\n" +
            "      \"duration\": 476,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 2,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1124639141\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1124639141/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/efe673d8e9f8090cf03d24ad1eaf573d/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/efe673d8e9f8090cf03d24ad1eaf573d/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/efe673d8e9f8090cf03d24ad1eaf573d/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/efe673d8e9f8090cf03d24ad1eaf573d/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"320c7379aab389b44c514b3d1482f287\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1124639141/tracks\",\n" +
            "      \"creation_date\": \"2015-01-21 15:57:12\",\n" +
            "      \"time_add\": 1465320395,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 909297265,\n" +
            "      \"title\": \"test 1 track\",\n" +
            "      \"duration\": 772,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 3,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/909297265\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/909297265/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/e055585ead99c75afa24c7c6b41a6768/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/e055585ead99c75afa24c7c6b41a6768/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/e055585ead99c75afa24c7c6b41a6768/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/e055585ead99c75afa24c7c6b41a6768/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"3ce6c08809b191e7f5cd409889c342f2\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/909297265/tracks\",\n" +
            "      \"creation_date\": \"2014-06-27 17:34:31\",\n" +
            "      \"time_add\": 1421855824,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 900217095,\n" +
            "      \"title\": \"test2\",\n" +
            "      \"duration\": 1215,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 6,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/900217095\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/900217095/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/1cdbc2f61d8e32c53fb552cdec0a5587-3cc228ca0b98ebfc25d813d7c6ab8fbe-6f8b47ab37b1407d788016681cef3d29-f0c8c466cd67c9ba32273230871e9f33/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/1cdbc2f61d8e32c53fb552cdec0a5587-3cc228ca0b98ebfc25d813d7c6ab8fbe-6f8b47ab37b1407d788016681cef3d29-f0c8c466cd67c9ba32273230871e9f33/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/1cdbc2f61d8e32c53fb552cdec0a5587-3cc228ca0b98ebfc25d813d7c6ab8fbe-6f8b47ab37b1407d788016681cef3d29-f0c8c466cd67c9ba32273230871e9f33/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/1cdbc2f61d8e32c53fb552cdec0a5587-3cc228ca0b98ebfc25d813d7c6ab8fbe-6f8b47ab37b1407d788016681cef3d29-f0c8c466cd67c9ba32273230871e9f33/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"195346db1875b979f8183d6ae7bc3255\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/900217095/tracks\",\n" +
            "      \"creation_date\": \"2014-06-17 10:10:09\",\n" +
            "      \"time_add\": 1424191876,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1546346631,\n" +
            "      \"title\": \"à tester\",\n" +
            "      \"duration\": 195,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 1,\n" +
            "      \"fans\": 0,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/1546346631\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/1546346631/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/d8be54e2c3140b823274a7e99652d89b/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/d8be54e2c3140b823274a7e99652d89b/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/d8be54e2c3140b823274a7e99652d89b/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/d8be54e2c3140b823274a7e99652d89b/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"f9ac1b8cec4ab1770fc2d714c26df814\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/1546346631/tracks\",\n" +
            "      \"creation_date\": \"2016-01-04 17:15:37\",\n" +
            "      \"time_add\": 1467276069,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 181720691,\n" +
            "        \"name\": \"François Blavoet\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/181720691/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 6529150,\n" +
            "      \"title\": \"Rock\",\n" +
            "      \"duration\": 25355,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 5,\n" +
            "      \"nb_tracks\": 112,\n" +
            "      \"fans\": 96,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/6529150\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/6529150/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/8b43f59170615ea5ac3578e38d9e36bf-cc921ec2e45fdb5e171c3953dba47b9b-f97b5c65d918fcb2a93ad1317fc1a7e0-be79cb766815f6de8ce34e62e37a2970/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/8b43f59170615ea5ac3578e38d9e36bf-cc921ec2e45fdb5e171c3953dba47b9b-f97b5c65d918fcb2a93ad1317fc1a7e0-be79cb766815f6de8ce34e62e37a2970/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/8b43f59170615ea5ac3578e38d9e36bf-cc921ec2e45fdb5e171c3953dba47b9b-f97b5c65d918fcb2a93ad1317fc1a7e0-be79cb766815f6de8ce34e62e37a2970/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/8b43f59170615ea5ac3578e38d9e36bf-cc921ec2e45fdb5e171c3953dba47b9b-f97b5c65d918fcb2a93ad1317fc1a7e0-be79cb766815f6de8ce34e62e37a2970/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"029e63f22fc8768e46656c443ceaf18e\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/6529150/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1399485837,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 1398451,\n" +
            "        \"name\": \"gatosek\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/1398451/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 9199935,\n" +
            "      \"title\": \"A la cool\",\n" +
            "      \"duration\": 24063,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 5,\n" +
            "      \"nb_tracks\": 103,\n" +
            "      \"fans\": 79,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/9199935\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/9199935/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/72c67ad486f359f84449e52582d8ebcc-57319ed1e88366becd59ff4919f92eb2-02d9e4d6710632b6383c6997f9919266-a9b5210e21ee263c9785ecc72a39bf60/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/72c67ad486f359f84449e52582d8ebcc-57319ed1e88366becd59ff4919f92eb2-02d9e4d6710632b6383c6997f9919266-a9b5210e21ee263c9785ecc72a39bf60/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/72c67ad486f359f84449e52582d8ebcc-57319ed1e88366becd59ff4919f92eb2-02d9e4d6710632b6383c6997f9919266-a9b5210e21ee263c9785ecc72a39bf60/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/72c67ad486f359f84449e52582d8ebcc-57319ed1e88366becd59ff4919f92eb2-02d9e4d6710632b6383c6997f9919266-a9b5210e21ee263c9785ecc72a39bf60/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"bb8416500e5eb6218112adb87d1d7e53\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/9199935/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1430348027,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 2520074,\n" +
            "        \"name\": \"Bio-Gosh\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/2520074/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 11626618,\n" +
            "      \"title\": \"Feet On The Table\",\n" +
            "      \"duration\": 7500,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 5,\n" +
            "      \"nb_tracks\": 34,\n" +
            "      \"fans\": 36,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/11626618\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/11626618/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/8e88cfb38f5e5fa1f14c3a43063e7d40-0b2b6ac5e5842d25582dee2664d21b8c-bf5a95f0963df16cd9fca01e629a4fed-e7e3467c5d3c104e76ecc94bc9abe586/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/8e88cfb38f5e5fa1f14c3a43063e7d40-0b2b6ac5e5842d25582dee2664d21b8c-bf5a95f0963df16cd9fca01e629a4fed-e7e3467c5d3c104e76ecc94bc9abe586/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/8e88cfb38f5e5fa1f14c3a43063e7d40-0b2b6ac5e5842d25582dee2664d21b8c-bf5a95f0963df16cd9fca01e629a4fed-e7e3467c5d3c104e76ecc94bc9abe586/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/8e88cfb38f5e5fa1f14c3a43063e7d40-0b2b6ac5e5842d25582dee2664d21b8c-bf5a95f0963df16cd9fca01e629a4fed-e7e3467c5d3c104e76ecc94bc9abe586/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"08972970f52ea32eb48bf55b99923327\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/11626618/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1429619725,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 3496160,\n" +
            "        \"name\": \"slunicko\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/3496160/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 22816083,\n" +
            "      \"title\": \"Aperisplif\",\n" +
            "      \"duration\": 14593,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 66,\n" +
            "      \"fans\": 2,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/22816083\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/22816083/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/b63bbf5d6c9428f9993329c7052ed5eb-07095a008c3a0f259dda20b8cf213a8d-ea185744084ec51a4db6834841a8f727-ba17632552b6c25d10b90a341f73ca09/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/b63bbf5d6c9428f9993329c7052ed5eb-07095a008c3a0f259dda20b8cf213a8d-ea185744084ec51a4db6834841a8f727-ba17632552b6c25d10b90a341f73ca09/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/b63bbf5d6c9428f9993329c7052ed5eb-07095a008c3a0f259dda20b8cf213a8d-ea185744084ec51a4db6834841a8f727-ba17632552b6c25d10b90a341f73ca09/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/b63bbf5d6c9428f9993329c7052ed5eb-07095a008c3a0f259dda20b8cf213a8d-ea185744084ec51a4db6834841a8f727-ba17632552b6c25d10b90a341f73ca09/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"d5a26e7b910015544221e117606de6f3\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/22816083/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 0,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 2536786,\n" +
            "        \"name\": \"picargogo60\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/2536786/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 38943560,\n" +
            "      \"title\": \"reggae posé\",\n" +
            "      \"duration\": 6811,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 24,\n" +
            "      \"fans\": 63,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/38943560\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/38943560/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/57319ed1e88366becd59ff4919f92eb2-be177a2c78b702b969d7fa7bf4d5d73f-88031e2005827f6cd6311a23679135b7-dd89dbfef86c0e76f4d06eba4fda6ebd/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/57319ed1e88366becd59ff4919f92eb2-be177a2c78b702b969d7fa7bf4d5d73f-88031e2005827f6cd6311a23679135b7-dd89dbfef86c0e76f4d06eba4fda6ebd/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/57319ed1e88366becd59ff4919f92eb2-be177a2c78b702b969d7fa7bf4d5d73f-88031e2005827f6cd6311a23679135b7-dd89dbfef86c0e76f4d06eba4fda6ebd/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/57319ed1e88366becd59ff4919f92eb2-be177a2c78b702b969d7fa7bf4d5d73f-88031e2005827f6cd6311a23679135b7-dd89dbfef86c0e76f4d06eba4fda6ebd/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"a94010bb2fac9599c25f5459ee2b17a1\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/38943560/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1436287684,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 9781916,\n" +
            "        \"name\": \"tha_doggy31\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/9781916/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 45926119,\n" +
            "      \"title\": \"Rock order\",\n" +
            "      \"duration\": 28262,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 133,\n" +
            "      \"fans\": 567,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/45926119\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/45926119/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/c936b2052b491987b958349709dc32c9-8de7fe34acf5658e79ccbb7695a24e54-fce6fe4cf02a3c78cedb8eb32fa4fa31-68378d9058edc787fdc08ef51e933b4e/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/c936b2052b491987b958349709dc32c9-8de7fe34acf5658e79ccbb7695a24e54-fce6fe4cf02a3c78cedb8eb32fa4fa31-68378d9058edc787fdc08ef51e933b4e/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/c936b2052b491987b958349709dc32c9-8de7fe34acf5658e79ccbb7695a24e54-fce6fe4cf02a3c78cedb8eb32fa4fa31-68378d9058edc787fdc08ef51e933b4e/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/c936b2052b491987b958349709dc32c9-8de7fe34acf5658e79ccbb7695a24e54-fce6fe4cf02a3c78cedb8eb32fa4fa31-68378d9058edc787fdc08ef51e933b4e/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"37ddd7a88d02d96f8af0d756e75de48d\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/45926119/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1398345465,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 9114523,\n" +
            "        \"name\": \"nazara94\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/9114523/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 60502351,\n" +
            "      \"title\": \"Playlist plusieurs morceaux - TEST\",\n" +
            "      \"duration\": 3502,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 15,\n" +
            "      \"fans\": 1,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/60502351\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/60502351/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/playlist/7267a8b04da3518a037961fb42844052/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/playlist/7267a8b04da3518a037961fb42844052/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/playlist/7267a8b04da3518a037961fb42844052/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/playlist/7267a8b04da3518a037961fb42844052/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"46c9b70bf93db58f906cee3c9f8db4c2\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/60502351/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 0,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 14388346,\n" +
            "        \"name\": \"JenPoc\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/14388346/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 62446954,\n" +
            "      \"title\": \"LOL\",\n" +
            "      \"duration\": 19665,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": true,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 83,\n" +
            "      \"fans\": 72,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/62446954\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/62446954/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/6f8b47ab37b1407d788016681cef3d29-1e6f6130ca0ccbdd0cde4dc2b05e6df9-fc52dae7da6a8349c82f37421a71f86b-715c524d9b3d51cef848068a1a57defc/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/6f8b47ab37b1407d788016681cef3d29-1e6f6130ca0ccbdd0cde4dc2b05e6df9-fc52dae7da6a8349c82f37421a71f86b-715c524d9b3d51cef848068a1a57defc/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/6f8b47ab37b1407d788016681cef3d29-1e6f6130ca0ccbdd0cde4dc2b05e6df9-fc52dae7da6a8349c82f37421a71f86b-715c524d9b3d51cef848068a1a57defc/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/6f8b47ab37b1407d788016681cef3d29-1e6f6130ca0ccbdd0cde4dc2b05e6df9-fc52dae7da6a8349c82f37421a71f86b-715c524d9b3d51cef848068a1a57defc/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"b1121895a713a560013a2a2d433a02ad\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/62446954/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1440494718,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 2369723,\n" +
            "        \"name\": \"Maximoy\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/2369723/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 63898051,\n" +
            "      \"title\": \"CHATTE\",\n" +
            "      \"duration\": 38114,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 5,\n" +
            "      \"nb_tracks\": 176,\n" +
            "      \"fans\": 114,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/63898051\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/63898051/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/playlist/495b4485fb01dd1a8f1f1bf412e778f3/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/playlist/495b4485fb01dd1a8f1f1bf412e778f3/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/playlist/495b4485fb01dd1a8f1f1bf412e778f3/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/playlist/495b4485fb01dd1a8f1f1bf412e778f3/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"98d7d3180237e906c717e9d85c1d1607\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/63898051/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1461576943,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 2913669,\n" +
            "        \"name\": \"Dimitri MENAGER\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/2913669/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 65059669,\n" +
            "      \"title\": \"Elite\",\n" +
            "      \"duration\": 10575,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 41,\n" +
            "      \"fans\": 362,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/65059669\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/65059669/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/57de3f98645077a70bedf7759492f447-b501012fc5653b441dd050c69aba1da3-23f9b1cda176a30eb53932193eb19ed5-7b8f0eea8760446ff790684aec7ab7e1/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/57de3f98645077a70bedf7759492f447-b501012fc5653b441dd050c69aba1da3-23f9b1cda176a30eb53932193eb19ed5-7b8f0eea8760446ff790684aec7ab7e1/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/57de3f98645077a70bedf7759492f447-b501012fc5653b441dd050c69aba1da3-23f9b1cda176a30eb53932193eb19ed5-7b8f0eea8760446ff790684aec7ab7e1/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/57de3f98645077a70bedf7759492f447-b501012fc5653b441dd050c69aba1da3-23f9b1cda176a30eb53932193eb19ed5-7b8f0eea8760446ff790684aec7ab7e1/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"03be0c85325cf28b779c4aef7f208771\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/65059669/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1467893843,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 12970795,\n" +
            "        \"name\": \"Théo Granado\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/12970795/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 66458211,\n" +
            "      \"title\": \"Comfortably Numb\",\n" +
            "      \"duration\": 35924,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 139,\n" +
            "      \"fans\": 213,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/66458211\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/66458211/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/e7e3467c5d3c104e76ecc94bc9abe586-d05a2c1897b3a139822e89edeafcb65d-157452365b8e1e9749501e9c9cf4b71f-6eb0a46b75485bae70d04b58d9a63ce2/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/e7e3467c5d3c104e76ecc94bc9abe586-d05a2c1897b3a139822e89edeafcb65d-157452365b8e1e9749501e9c9cf4b71f-6eb0a46b75485bae70d04b58d9a63ce2/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/e7e3467c5d3c104e76ecc94bc9abe586-d05a2c1897b3a139822e89edeafcb65d-157452365b8e1e9749501e9c9cf4b71f-6eb0a46b75485bae70d04b58d9a63ce2/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/e7e3467c5d3c104e76ecc94bc9abe586-d05a2c1897b3a139822e89edeafcb65d-157452365b8e1e9749501e9c9cf4b71f-6eb0a46b75485bae70d04b58d9a63ce2/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"11b269129c46fdbec6cc4d97119dffa5\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/66458211/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1420286392,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 1120082,\n" +
            "        \"name\": \"Boss \",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/1120082/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 69751191,\n" +
            "      \"title\": \"At Home\",\n" +
            "      \"duration\": 7151,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 4,\n" +
            "      \"nb_tracks\": 30,\n" +
            "      \"fans\": 456,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/69751191\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/69751191/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/playlist/61c033aa9e9a0d4e9d25882df656536c/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/playlist/61c033aa9e9a0d4e9d25882df656536c/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/playlist/61c033aa9e9a0d4e9d25882df656536c/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/playlist/61c033aa9e9a0d4e9d25882df656536c/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"8abdbcfa75c21a408b8e58a9adab4679\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/69751191/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1386583006,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 10143044,\n" +
            "        \"name\": \"leaventura\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/10143044/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 78466011,\n" +
            "      \"title\": \"Midnight\",\n" +
            "      \"duration\": 38039,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 136,\n" +
            "      \"fans\": 467,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/78466011\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/78466011/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/playlist/bf82459011066c0c0e0f7c995cb00ab3/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/playlist/bf82459011066c0c0e0f7c995cb00ab3/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/playlist/bf82459011066c0c0e0f7c995cb00ab3/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/playlist/bf82459011066c0c0e0f7c995cb00ab3/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"4a915caa44ecafe3dcb47a6c13c59a9d\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/78466011/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1424521360,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 725532651,\n" +
            "        \"name\": \"Deezer Editors - Playlists Archives\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/725532651/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 113403371,\n" +
            "      \"title\": \"playa sublime\",\n" +
            "      \"duration\": 5480,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 21,\n" +
            "      \"fans\": 146,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/113403371\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/113403371/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/37e0a38985bb1b0d5a7d2d98efa1e1b7-3953e4d2524ea7e0cbaecf7d942214e1-d6b9605ee954048ab5cb2e6be7bfe487-9d618d4557ec3000529d1c05a98f2de4/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/37e0a38985bb1b0d5a7d2d98efa1e1b7-3953e4d2524ea7e0cbaecf7d942214e1-d6b9605ee954048ab5cb2e6be7bfe487-9d618d4557ec3000529d1c05a98f2de4/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/37e0a38985bb1b0d5a7d2d98efa1e1b7-3953e4d2524ea7e0cbaecf7d942214e1-d6b9605ee954048ab5cb2e6be7bfe487-9d618d4557ec3000529d1c05a98f2de4/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/37e0a38985bb1b0d5a7d2d98efa1e1b7-3953e4d2524ea7e0cbaecf7d942214e1-d6b9605ee954048ab5cb2e6be7bfe487-9d618d4557ec3000529d1c05a98f2de4/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"7600b523bc046f574af5f80267863ed8\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/113403371/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1388061566,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 66795771,\n" +
            "        \"name\": \"Oscar Cabello Castro\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/66795771/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 179170231,\n" +
            "      \"title\": \"Yeah\",\n" +
            "      \"duration\": 9606,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": false,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 51,\n" +
            "      \"fans\": 144,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/179170231\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/179170231/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/cover/922d268e5b89bf65c61f658e499f8bf5-9e472cbd83d304ed34f9ccc394edb4a5-eca5f30c9a9c3cec8bfb9f0008c2e37f-5c1bdcd4936226b3fc6effa9cf4bcb05/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/cover/922d268e5b89bf65c61f658e499f8bf5-9e472cbd83d304ed34f9ccc394edb4a5-eca5f30c9a9c3cec8bfb9f0008c2e37f-5c1bdcd4936226b3fc6effa9cf4bcb05/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/cover/922d268e5b89bf65c61f658e499f8bf5-9e472cbd83d304ed34f9ccc394edb4a5-eca5f30c9a9c3cec8bfb9f0008c2e37f-5c1bdcd4936226b3fc6effa9cf4bcb05/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/cover/922d268e5b89bf65c61f658e499f8bf5-9e472cbd83d304ed34f9ccc394edb4a5-eca5f30c9a9c3cec8bfb9f0008c2e37f-5c1bdcd4936226b3fc6effa9cf4bcb05/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"7e815820d1bc6277645a48975115193a\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/179170231/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1412666150,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 6389975,\n" +
            "        \"name\": \"klmair\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/6389975/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 540107435,\n" +
            "      \"title\": \"GTA V Radio Tracks\",\n" +
            "      \"duration\": 21853,\n" +
            "      \"public\": true,\n" +
            "      \"is_loved_track\": false,\n" +
            "      \"collaborative\": true,\n" +
            "      \"rating\": 0,\n" +
            "      \"nb_tracks\": 87,\n" +
            "      \"fans\": 17,\n" +
            "      \"link\": \"http://www.deezer.com/playlist/540107435\",\n" +
            "      \"picture\": \"http://api.deezer.com/2.0/playlist/540107435/image\",\n" +
            "      \"picture_small\": \"http://cdn-images.deezer.com/images/playlist/49d6829ec35721aad218bf8ddc3413d9/56x56-000000-80-0-0.jpg\",\n" +
            "      \"picture_medium\": \"http://cdn-images.deezer.com/images/playlist/49d6829ec35721aad218bf8ddc3413d9/250x250-000000-80-0-0.jpg\",\n" +
            "      \"picture_big\": \"http://cdn-images.deezer.com/images/playlist/49d6829ec35721aad218bf8ddc3413d9/500x500-000000-80-0-0.jpg\",\n" +
            "      \"picture_xl\": \"http://cdn-images.deezer.com/images/playlist/49d6829ec35721aad218bf8ddc3413d9/1000x1000-000000-80-0-0.jpg\",\n" +
            "      \"checksum\": \"a2a68a0c627b39400511c3a73e40f9c7\",\n" +
            "      \"tracklist\": \"http://api.deezer.com/2.0/playlist/540107435/tracks\",\n" +
            "      \"creation_date\": \"0000-00-00 00:00:00\",\n" +
            "      \"time_add\": 1388529157,\n" +
            "      \"creator\": {\n" +
            "        \"id\": 3338293,\n" +
            "        \"name\": \"canderso\",\n" +
            "        \"tracklist\": \"http://api.deezer.com/2.0/user/3338293/flow\",\n" +
            "        \"type\": \"user\"\n" +
            "      },\n" +
            "      \"type\": \"playlist\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"checksum\": \"b9dd7936ee9cf711d8190e3704e8e76e011a4e94e456a8d415372cb8c79be19f\",\n" +
            "  \"total\": 88,\n" +
            "  \"next\": \"http://api.deezer.com/2.0/user/21292726/playlists?index=25\"\n" +
            "}";

    // endregion
}