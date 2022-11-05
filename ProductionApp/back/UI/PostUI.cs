using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.UI.Interfaces;

namespace PyxisKapriBack.UI
{
    public class PostUI : IPostUI
    {
        private readonly IPostService postService;
        private readonly ILikeService likeService;

        public PostUI(IPostService postService, ILikeService likeService)
        {
            this.postService = postService;
            this.likeService = likeService;
        }
        public void AddPost(NewPostDTO post)
        {
            postService.AddPost(post); 
        }

        public void DeletePost(int postID)
        {
            postService.DeletePost(postID);
        }

        public bool DeleteUserPost(int postID, string userName)
        {
            return postService.DeleteUserPost(postID, userName);
        }

        public Post GetPost(int PostID)
        {
            return postService.GetPost(PostID); 

           
        }

        public List<PostDTO> GetPostsForLocation(int LocationID)
        {
            var posts = postService.GetPostsForLocation(LocationID);
            var postsDTO = new List<PostDTO>();

            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                    CoverImage = post.CoverImage,
                    NumberOfLikes = likeService.GetNumberOfLikesByPostID(post.Id),
                    NumberOfViews = 0
                });
            }

            return postsDTO;
        }

        public List<PostDTO> GetUserPosts(string username)
        {
            var posts = postService.GetUserPosts(username);
            var postsDTO = new List<PostDTO>();

            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                    CoverImage = post.CoverImage,
                    NumberOfLikes = likeService.GetNumberOfLikesByPostID(post.Id),
                    NumberOfViews = 0
                });
            }

            return postsDTO;
        }

        public void SetLikeOnPost(int postID)
        {
            postService.SetLikeOnPost(postID);
        }
    }
}
