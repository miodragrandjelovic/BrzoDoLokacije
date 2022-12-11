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
        private readonly IUserService userService;

        public PostUI(IPostService postService, ILikeService likeService, IUserService userService)
        {
            this.postService = postService;
            this.likeService = likeService;
            this.userService = userService;
        }
        public void AddPost(NewPostDTO post)
        {
            postService.AddPost(post); 
        }

        public Response DeletePost(int postID)
        {
            return postService.DeletePost(postID);
        }

        public Response DeleteUserPost(int postID)
        {
            return postService.DeleteUserPost(postID);
        }

        public List<PostDTO> GetAllPosts(SortType sortType = SortType.DATE)
        {
            var posts = postService.GetAllPosts(sortType);

            var allPosts = new List<PostDTO>();

            foreach (var post in posts)
            {
                allPosts.Add(new PostDTO
                {
                    Id = post.Id,
                    FullProfileImagePath = Path.Combine(post.User.FolderPath,post.User.FileName),
                    NumberOfLikes = Math.Round((double)(post.Likes.Count > 0 ? post.Likes.Sum(post => post.Grade) * 1.0 / post.Likes.Count() : 0), 2),
                    NumberOfViews = 0,
                    AverageGrade = postService.GetAverageGrade(post.Id), 
                    Grade = postService.GetGrade(post.Id, userService.GetLoggedUser()),
                    Username = post.User.Username,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath,post.PostPath,post.CoverImageName),
                    IsLiked = likeService.IsLiked(post.Id, userService.GetLoggedUser()),
                    DateCreated = post.CreatedDate.ToString("g"),
                    Count = post.Likes.Count(), 
                    Tags = post.Tags
                });
            }
            return allPosts;
        }

        public List<PostDTO> GetFollowingPosts(SortType sortType = SortType.DATE)
        {
            var posts = postService.GetFollowingPosts(userService.GetLoggedUser(), sortType);

            var allPosts = new List<PostDTO>();
            var username = userService.GetLoggedUser();
            foreach (var post in posts)
            {
                allPosts.Add(new PostDTO
                {
                    Id = post.Id,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    NumberOfLikes = Math.Round((double)(post.Likes.Count > 0 ? post.Likes.Sum(post => post.Grade) * 1.0 / post.Likes.Count() : 0), 2),
                    NumberOfViews = 0,
                    AverageGrade = postService.GetAverageGrade(post.Id),
                    Username = post.User.Username,
                    FullProfileImagePath = Path.Combine(post.User.FolderPath, post.User.FileName),
                    IsLiked = likeService.IsLiked(post.Id, username),
                    Grade = postService.GetGrade(post.Id, username),
                    DateCreated = post.CreatedDate.ToString("g"),
                    Count = post.Likes.Count(), 
                    Tags = post.Tags
                });
            }
            return allPosts;
        }
        public Response GetRecommendedPosts(SortType sortType = SortType.DATE)
        {
            Response response = postService.GetRecommendedPosts(userService.GetLoggedUser(), sortType); 
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                response.Data = createPostDTOList(response.Data.Cast<Post>().ToList()).Cast<object>().ToList();
            return response;
        }
        public AdditionalPostData GetPost(int PostID)
        {
            var post = postService.GetPost(PostID);
            if(post == null)
                return null;
            var images = new List<string>();

            foreach (var image in post.Images)
            {
                var fullPath = Path.Combine(post.User.FolderPath, post.PostPath, image.ImageName);
                images.Add(fullPath); 
            }
            return new AdditionalPostData
            {
                Description = post.Description,
                Latitude = post.Location.Latitude,
                Longitude = post.Location.Longitude,
                Address = post.Location.Address,
                City = string.Empty,
                Country = string.Empty,
                Images = images,
                NumberOfComments = post.Comments != null ? post.Comments.Count() : 0
            };

           
        }

        public List<PostDTO> GetPostsForLocation(int LocationID)
        {
            var posts = postService.GetPostsForLocation(LocationID);
            var postsDTO = new List<PostDTO>();

            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                   // CoverImage = post.CoverImage,
                    NumberOfLikes = Math.Round((double)(post.Likes.Count > 0 ? post.Likes.Sum(post => post.Grade) * 1.0 / post.Likes.Count() : 0), 2),
                    NumberOfViews = 0,
                    AverageGrade = postService.GetAverageGrade(post.Id)
                });
            }

            return postsDTO;
        }

        public Response GetUserPosts(string username)
        {
            Response response = postService.GetUserPosts(username);
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                response.Data = createPostDTOList(response.Data.Cast<Post>().ToList()).Cast<object>().ToList(); 
            return response;
        }

        public Response RemoveLikeFromPost(int postID)
        {
            return likeService.DeleteLike(postID);
        }

        public Response GetPostsOnMap(string username = "")
        {
            Response response;
            if (string.IsNullOrEmpty(username))
                response = postService.GetUserPosts(userService.GetLoggedUser());
            else
                response = postService.GetUserPosts(username);

            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                response.Data = createPostOnMapDTO(response.Data.Cast<Post>().ToList(), username).Cast<object>().ToList();
            return response;
        }

        private List<PostDTO> createPostDTOList(List<Post> posts)
        {
            var postsDTO = new List<PostDTO>();
            if (posts == null)
                return null;
            var username = userService.GetLoggedUser(); 
            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                    Id = post.Id,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    NumberOfLikes = Math.Round((double)(post.Likes.Count > 0 ? post.Likes.Sum(post => post.Grade) * 1.0 / post.Likes.Count() : 0), 2),
                    NumberOfViews = 0,
                    AverageGrade = postService.GetAverageGrade(post.Id),
                    Grade = postService.GetGrade(post.Id, username), 
                    Username = post.User.Username.ToString(),
                    FullProfileImagePath = Path.Combine(post.User.FolderPath, post.User.FileName),
                    DateCreated = post.CreatedDate.ToString("g"),
                    IsLiked = likeService.IsLiked(post.Id, username),
                    Count = post.Likes.Count(), 
                    Tags = post.Tags
                });
            }

            return postsDTO;
        }
        private List<PostDTO> createPostDTOListWithLocation(List<Post> posts)
        {
            var postsDTO = new List<PostDTO>();
            if (posts == null)
                return null;
            foreach (var post in posts)
            {
                postsDTO.Add(new PostDTO
                {
                    Id = post.Id,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    NumberOfLikes = Math.Round((double)(post.Likes.Count > 0 ? post.Likes.Sum(post => post.Grade) * 1.0 / post.Likes.Count() : 0), 2),
                    AverageGrade = postService.GetAverageGrade(post.Id), 
                    DateCreated = post.CreatedDate.ToString("g"),
                    Location = post.Location.Name, 
                    City = post.Location.City.Name, 
                    Country = post.Location.City.Country.Name, 
                    Tags = post.Tags
                });
            }

            return postsDTO;
        }


        private List<PostOnMapDTO> createPostOnMapDTO(List<Post> posts, String username)
        {
            var postsDTO = new List<PostOnMapDTO>();
            if (posts == null)
                return null;
            foreach (var post in posts)
            {
                postsDTO.Add(new PostOnMapDTO
                {
                    Id = post.Id,
                    CoverImagePath = Path.Combine(Constants.Constants.ROOT_FOLDER, username, post.PostPath, post.CoverImageName),
                    Latitude = post.Latitude,
                    Longitude = post.Longitude,
                    numberOfLikes = Math.Round((double)(post.Likes.Count > 0 ? post.Likes.Sum(post => post.Grade)*1.0 / post.Likes.Count() : 0), 2)
                }) ;
            }

            return postsDTO; 
        }

        public Response GetPostsBySearch(string search, SearchType searchType = SearchType.LOCATION, SortType sortType = SortType.DATE, int countOfResult = Constants.Constants.TAKE_ELEMENT, bool friendsOnly = false)
        {
            Response response = postService.GetPostsBySearch(search, searchType, sortType, countOfResult, friendsOnly);
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                response.Data = createPostOnMapDTO(response.Data.Cast<Post>().ToList(), userService.GetLoggedUser()).Cast<object>().ToList();
            return response;
        }

        public PostDTO GetPostById(int postID)
        {
            var post = postService.GetPost(postID);
            var username = userService.GetLoggedUser(); 
            if (post != null)
                return new PostDTO
                {
                    Id = post.Id,
                    FullProfileImagePath = Path.Combine(post.User.FolderPath, post.User.FileName),
                    NumberOfLikes = post.Likes != null ? post.Likes.Count() : 0,
                    NumberOfViews = 0,
                    AverageGrade = postService.GetAverageGrade(postID),
                    Grade = postService.GetGrade(post.Id, username),
                    Username = post.User.Username,
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    IsLiked = likeService.IsLiked(post.Id, username),
                    DateCreated = post.CreatedDate.ToString("g"),
                    Count = post.Likes.Count(), 
                    Tags = post.Tags
                };
            return null;
        }

        public List<PostOnMapDTO> GetAllAroundPosts(double latitude, double longitude, double distance = Constants.Constants.DISTANCE, bool friendsOnly = false)
        {
            var posts = postService.GetAllAroundPosts(latitude, longitude, distance, friendsOnly);
            
            return createPostOnMapDTO(posts, userService.GetLoggedUser()).ToList(); 
        }

        public LikeDTO SetLikeOnPost(LikeDTO likeDTO)
        {
            likeDTO.Username = userService.GetLoggedUser(); 
            var code = postService.SetLikeOnPost(likeDTO).StatusCode;
            if (code.Equals(StatusCodes.Status200OK))
            {
                return new LikeDTO
                {
                    Average = postService.GetAverageGrade(likeDTO.PostId),
                    Grade = likeDTO.Grade,
                    Count = postService.GetPost(likeDTO.PostId).Likes.Count()
                };
            }
            return null;
        }

        public List<StatisticsDTO> GetUserTopPosts(string username)
        {
            var response = postService.GetUserPosts(username, SortType.COUNT_LIKES);
            List<Post> posts = null; 
            if (response.StatusCode.Equals(StatusCodes.Status200OK))
                posts = response.Data.Cast<Post>().Take(3).ToList();

            var statisticsDTO = new List<StatisticsDTO>();
            if (posts == null)
                return null;

            foreach (var post in posts)
            {
                statisticsDTO.Add(new StatisticsDTO
                {
                    FullCoverImagePath = Path.Combine(post.User.FolderPath, post.PostPath, post.CoverImageName),
                    AverageGrade = postService.GetAverageGrade(post.Id),
                    Count = post.Likes.Count()
                });
            }

            return statisticsDTO;
           
        }
    }
}
