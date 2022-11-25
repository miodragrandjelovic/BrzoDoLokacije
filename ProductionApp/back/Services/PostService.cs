using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.DTOComponents;
using System.Text;

namespace PyxisKapriBack.Services
{
    public class PostService : IPostService
    {
        private readonly IPostDAL postDAL;
        private readonly ILikeService likeService;
        private readonly IUserService userService;
        private readonly ILocationDAL locationDAL;
        private readonly ICityDAL cityDAL;
        private readonly ICountryDAL countryDAL;
        private readonly IFileService fileService;

        public PostService(IPostDAL postDAL, ILikeService likeService, IUserService userService, ILocationDAL locationDAL, 
            ICityDAL cityDAL, ICountryDAL countryDAL,IFileService fileService)
        {
            this.postDAL = postDAL;
            this.likeService = likeService;
            this.userService = userService;
            this.locationDAL = locationDAL;
            this.cityDAL = cityDAL;
            this.countryDAL = countryDAL;
            this.fileService = fileService;
        }

        public void AddPost(NewPostDTO post)
        {
            var loggedUser = userService.GetUser(userService.GetLoggedUser());
            var postPath = fileService.GetPostName();
            var newPost = new Post();
            newPost.User = loggedUser;
            newPost.CreatedDate = DateTime.Now;
            newPost.Description = post.Description;
            newPost.CoverImageName = post.CoverImage.FileName;
            newPost.PostPath = postPath;

            var fullPath = Path.Combine(loggedUser.FolderPath, postPath);
            var answer = fileService.CreateFolder(fullPath);
            //if (!answer)
            //    return new Response
            //    {
            //        StatusCode = StatusCodes.Status500InternalServerError,
            //        Message = "Error while creating post folder!"
            //    };
            fileService.AddFile(fullPath, post.CoverImage);



            var location = locationDAL.GetLocation(post.LocationName);
            var city = cityDAL.GetCity(post.City);
            var country = countryDAL.GetCountry(post.Country);
            
            if (country == null)
                if (countryDAL.AddCountry(post.Country))
                    country = countryDAL.GetCountry(post.Country);

            if (city == null)
                if (cityDAL.AddCity(post.City, post.Country))
                    city = cityDAL.GetCity(post.City);

            if (location == null)
            {
                location = new Location();
                location.Longitude = Convert.ToDouble(post.Longitude);
                location.Latitude = Convert.ToDouble(post.Latitude);
                location.City = city; 
                location.Address = post.Address;
                location.Name = post.LocationName;

                locationDAL.AddLocation(location); 
            }
            else if((location.Longitude == 0) || (location.Latitude == 0))
            {
                location.Longitude = Convert.ToDouble(post.Longitude);
                location.Latitude = Convert.ToDouble(post.Latitude);
                locationDAL.UpdateLocation(location); 
            }
            else if (String.IsNullOrEmpty(location.Address))
            {
                location.Address = post.Address;
                locationDAL.AddLocation(location);
            }

            newPost.Location = location;
                
            if (post.Images.Count > 0)
            {
                foreach (var image in post.Images)
                {
                    Image newImage = new Image();
                    newImage.ImageName = image.FileName;
                    fileService.AddFile(fullPath, image);
                    newPost.Images.Add(newImage);
                }
            }


            postDAL.AddPost(newPost);
        }
        public Response DeletePost(int postID)
        {
            bool succeed = postDAL.DeletePost(postID);
            if (!succeed)
                return new Response
                {
                    StatusCode = StatusCodes.Status500InternalServerError,
                    Message = Constants.Constants.resNoFoundPost
                };

            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "Post deleted succesffuly!"
            };
        }
        public Response DeleteUserPost(int postID)
        {
            var response = new Response();
            response.StatusCode = StatusCodes.Status404NotFound;

            var user = userService.GetUser(userService.GetLoggedUser());
            var post = GetPost(postID);

            
            if (user == null)
            {
                response.Message = Constants.Constants.resNoFoundUser;
                return response;
            }

            if (post == null)
            {
                response.Message = Constants.Constants.resNoFoundPost;
                return response;
            }
            if(!user.Id.Equals(post.UserId))
            {
                response.StatusCode=StatusCodes.Status403Forbidden;
                response.Message = "Permission denied!";
                return response;
            }

            postDAL.DeletePost(postID);

            response.StatusCode = StatusCodes.Status200OK;
            response.Message = "Post deleted succesffuly!";
            return response;

        }
        public List<Post> GetAllPosts()
        {
            return postDAL.GetPosts(userService.GetLoggedUser());
        }
        public Post GetPost(int PostID)
        {
            return postDAL.GetPost(PostID);
        }
        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }
        public List<Post> GetUserPosts(string username)
        {
            return postDAL.GetUserPosts(username);
        }

        public Response SetLikeOnPost(int postID)
        {
            return likeService.AddLike(postID);
        }
        public List<Post> GetFollowingPosts(string username)
        {
            return postDAL.GetFollowingPosts(username); 
        }
        public List<Post> GetRecommendedPosts(string username)
        {
            return postDAL.GetRecommendedPosts(username); 
        }
    }
}
