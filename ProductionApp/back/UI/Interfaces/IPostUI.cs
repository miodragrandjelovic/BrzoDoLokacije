using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.UI.Interfaces
{
    public interface IPostUI
    {
        Response GetUserPosts(string username);
        List<PostDTO> GetPostsForLocation(int LocationID);
        void AddPost(NewPostDTO post);
        Response DeletePost(int postID);
        AdditionalPostData GetPost(int PostID);
        PostDTO GetPostById(int postID);
        // Response SetLikeOnPost(int postID);
        LikeDTO SetLikeOnPost(LikeDTO likeDTO); 
        Response DeleteUserPost(int postID);
        List<PostDTO> GetAllPosts(SortType sortType = SortType.DATE);
        Response RemoveLikeFromPost(int postID);
        List<PostDTO> GetFollowingPosts(SortType sortType = SortType.DATE);
        Response GetRecommendedPosts(SortType sortType = SortType.DATE);
        Response GetPostsOnMap(string username = "");
        Response GetPostsBySearch(string search, SearchType searchType = SearchType.LOCATION, SortType sortType = SortType.DATE, int countOfResult = Constants.Constants.TAKE_ELEMENT, bool friendsOnly = false);

        List<PostOnMapDTO> GetAllAroundPosts(double latitude, double longitude, double distance = Constants.Constants.DISTANCE, bool friendsOnly = false);

        List<StatisticsDTO> GetUserTopPosts(string username); 
    }
}
