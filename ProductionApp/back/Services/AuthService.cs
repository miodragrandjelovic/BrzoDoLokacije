using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.JWTManager.Interfaces;
using PyxisKapriBack.Models;
using PyxisKapriBack.Models.Interfaces;
using PyxisKapriBack.Services.Interfaces;

namespace PyxisKapriBack.Services
{
    public class AuthService : IAuthService
    {
        private readonly IUserService userService;
        private readonly IEncryptionManager encryptionManager;
        private readonly IJWTManagerRepository JWTManager;

        public AuthService(IUserService userService, IEncryptionManager encryptionManager, IJWTManagerRepository JWTManager)
        {
            this.userService = userService;
            this.encryptionManager = encryptionManager;
            this.JWTManager = JWTManager;
        }

        public async Task<Response> Login(LoginDTO request)
        {
            var response = new Response();  
            var user = userService.GetUser(request.UsernameOrEmail);
            if (user == null)
            {
                response.StatusCode = StatusCodes.Status404NotFound;
                response.Message = "User doesn't exist!";
                return response;
            }

            if (!encryptionManager.DecryptPassword(request.Password, user.Password, user.PasswordKey))
            {
                response.StatusCode= StatusCodes.Status403Forbidden;
                response.Message = "Wrong password!";
                return response;
            }


            var tokenString = JWTManager.GenerateToken(user);
            response.StatusCode = StatusCodes.Status200OK;
            response.Message = tokenString;
            return response;
            
        }

        public async Task<Response> Register(RegisterDTO request)
        {
            var response = new Response();
            byte[] passwordHash, passwordKey;
            if (await userService.UserAlreadyExists(request.Username))
            {
                response.StatusCode = StatusCodes.Status400BadRequest;
                response.Message = "User already exists";
            }

            encryptionManager.EncryptPassword(request.Password, out passwordHash, out passwordKey);

            User newUser = new User
            {
                Username = request.Username,
                FirstName = request.FirstName,
                LastName = request.LastName,
                Password = passwordHash,
                PasswordKey = passwordKey,
                Email = request.Email,
                Role = userService.GetUserRole(),
                CountryId = Constants.ModelConstants.DEFAULT

            };
            userService.AddNewUser(newUser);
            return new Response
            {
                StatusCode = StatusCodes.Status200OK,
                Message = "User added successfully!"
            };
        }
    }
}
