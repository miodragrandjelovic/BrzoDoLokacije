using PyxisKapriBack.DTOComponents;
using PyxisKapriBack.Models;

namespace PyxisKapriBack.Services.Interfaces
{
    public interface IAuthService
    {
        Task<Response> Register(RegisterDTO request);
        Task<Response> Login(LoginDTO request);
    }
}
