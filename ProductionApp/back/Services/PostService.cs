using PyxisKapriBack.DAL.Interfaces;
using PyxisKapriBack.Services.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.DTOComponents;
using System.Text;
using PyxisKapriBack.PythonService;

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
        private readonly ServiceClient client;

        public PostService(IPostDAL postDAL, ILikeService likeService, IUserService userService, ILocationDAL locationDAL, 
            ICityDAL cityDAL, ICountryDAL countryDAL,IFileService fileService,ServiceClient client)
        {
            this.postDAL = postDAL;
            this.likeService = likeService;
            this.userService = userService;
            this.locationDAL = locationDAL;
            this.cityDAL = cityDAL;
            this.countryDAL = countryDAL;
            this.fileService = fileService;
            this.client = client;
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
            newPost.Longitude = Convert.ToDouble(post.Longitude);
            newPost.Latitude = Convert.ToDouble(post.Latitude);
            newPost.FullLocation = post.LocationName; 

            var fullPath = Path.Combine(loggedUser.FolderPath, postPath);
            var answer = fileService.CreateFolder(fullPath);
            fileService.AddFile(fullPath, post.CoverImage);

            //var response = client.DoFacesExistOnImage(Path.Combine(Directory.GetCurrentDirectory(), fullPath, post.CoverImage.FileName)).Result;
            //if(response == true)
            //    Console.WriteLine("Pronadjena lica");
            client.SendPathToService(Path.Combine(Directory.GetCurrentDirectory(), fullPath, post.CoverImage.FileName).ToString());
            // poziv py servisa za kompresiju slika


            newPost.Location = FixLocation(post.Address, post.LocationName, post.City, post.Country, 
                                           Convert.ToDouble(post.Longitude), Convert.ToDouble(post.Latitude)); 
            
            if (post.Images.Count > 0)
            {
                foreach (var image in post.Images)
                {
                    fileService.AddFile(fullPath, image);
                    bool exists = client.DoFacesExistOnImage(Path.Combine(Directory.GetCurrentDirectory(), fullPath, image.FileName)).Result;
                    if (exists == true)
                        File.Delete(Path.Combine(Directory.GetCurrentDirectory(), fullPath, image.FileName));
                    else
                    {
                        Image newImage = new Image();
                        newImage.ImageName = image.FileName;
                        
                        newPost.Images.Add(newImage);

                        client.SendPathToService(Path.Combine(Directory.GetCurrentDirectory(), fullPath, image.FileName).ToString());
                    }
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
        private Location FixLocation(string address, string locationName, string cityName, string countryName, double longitude, double latitude)
        {
            var location = locationDAL.GetLocation(locationName);
            if(location == null)
                location = locationDAL.GetLocation(address);

            var city = cityDAL.GetCity(cityName);
            var country = countryDAL.GetCountry(countryName);

            if (country == null)
                if (countryDAL.AddCountry(countryName))
                    country = countryDAL.GetCountry(countryName);

            if (city == null)
                if (cityDAL.AddCity(cityName, countryName))
                    city = cityDAL.GetCity(cityName);

            if (location == null)
            {
                location = new Location();
                location.Longitude = Convert.ToDouble(longitude);
                location.Latitude = Convert.ToDouble(latitude);
                location.City = city;
                location.Address = address;

                if (String.IsNullOrEmpty(locationName))
                {
                    if (String.IsNullOrEmpty(address))
                        location.Name = Constants.Constants.UNKNWOWN;
                    else
                        location.Name = address;
                }
                else
                    location.Name =locationName;

                locationDAL.AddLocation(location);
            }
            else
            {
                if (String.IsNullOrEmpty(location.Address))
                    location.Address = address;

                if (String.IsNullOrEmpty(location.Name) && (!String.IsNullOrEmpty(locationName)))
                    location.Name = locationName;

                if ((location.Longitude == 0) || (location.Latitude == 0))
                {
                    location.Longitude = Convert.ToDouble(longitude);
                    location.Latitude = Convert.ToDouble(latitude);
                }

                if (location.City == null)
                    location.City = city;
                locationDAL.UpdateLocation(location);
            }

            return location; 
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
        public List<Post> GetAllPosts(SortType sortType = SortType.DATE)
        {
            return postDAL.GetPosts(userService.GetLoggedUser(), sortType);
        }
        public Post GetPost(int PostID)
        {
            return postDAL.GetPost(PostID);
        }
        public List<Post> GetPostsForLocation(int LocationID)
        {
            return postDAL.GetPostsForLocation(LocationID);
        }
        public Response GetUserPosts(string username)
        {
            var response = new Response();
            try
            {
                response.Data = postDAL.GetUserPosts(username).Cast<object>().ToList();
                response.StatusCode = StatusCodes.Status200OK;
                response.Message = "Found posts"; 
            }
            catch(Exception e)
            {
                response.StatusCode = StatusCodes.Status500InternalServerError;
                response.Message = e.Message; 
            }

            return response; 
        }
        public Response SetLikeOnPost(int postID)
        {
            return likeService.AddLike(postID);
        }
        public List<Post> GetFollowingPosts(string username, SortType sortType = SortType.DATE)
        {
            return postDAL.GetFollowingPosts(username, sortType); 
        }
        public Response GetRecommendedPosts(string username, SortType sortType = SortType.DATE)
        {
            var response = new Response();
            try
            {
                response.Data = postDAL.GetRecommendedPosts(username, sortType).Cast<object>().ToList();
                response.Message = "Found posts";
                response.StatusCode = StatusCodes.Status200OK;
               
            }
            catch (Exception e)
            {
                response.StatusCode = StatusCodes.Status500InternalServerError;
                response.Message = e.Message;
            }

            return response;
        }

        public Response GetPostsBySearch(String search, SortType sortType = SortType.DATE, int countOfResult = Constants.Constants.TAKE_ELEMENT)
        {
            var response = new Response();

            try
            {
                if (countOfResult == 0)
                    countOfResult = Constants.Constants.TAKE_ELEMENT; 
                response.Data = postDAL.GetPostsBySearch(search, sortType).Take(countOfResult).Cast<object>().ToList(); 
                response.Message = "Found posts";
                response.StatusCode = StatusCodes.Status200OK;
            }
            catch(Exception e)
            {
                response.Message = "Found posts";
                response.StatusCode = StatusCodes.Status500InternalServerError;
            }

            return response; 
        }
    }
}
