﻿using Microsoft.IdentityModel.Tokens;
using PyxisKapriBack.JWTManager.Interfaces;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace PyxisKapriBack.JWTManager
{
    public class JWTManagerRepository : IJWTManagerRepository
    {
        private readonly IConfiguration configuration;
        public JWTManagerRepository(IConfiguration configuration)
        {
            this.configuration = configuration;
        }
        public string GenerateToken(User user)
        {
            List<Claim> claims = new List<Claim>
            {
                new Claim(Constants.Constants.USERNAME, user.Username),
                new Claim(Constants.Constants.EMAIL, user.Email),
                new Claim(Constants.Constants.ROLE, user.Role.Name)
                
            };

            var key = new SymmetricSecurityKey(System.Text.Encoding.UTF8.GetBytes(configuration.GetSection("AppSettings:Token").Value));

            var cred = new SigningCredentials(key, SecurityAlgorithms.HmacSha512);

            var token = new JwtSecurityToken(
                claims: claims,
                expires: DateTime.Now.AddDays(1),
                signingCredentials: cred
            );

            var jwt = new JwtSecurityTokenHandler().WriteToken(token);


            return jwt;
        }

        public string? ReadToken(string token)
        {
            if (token.IsNullOrEmpty())
                return null;


            var tokenHandler = new JwtSecurityTokenHandler();

            var key = Encoding.UTF8.GetBytes(configuration.GetSection("AppSettings:Token").Value);

            try
            {
                tokenHandler.ValidateToken(token, new TokenValidationParameters
                {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(key),
                    ValidateIssuer = false,
                    ValidateAudience = false,
                    ClockSkew = TimeSpan.Zero
                }, out SecurityToken validatedToken); ;

                var jwtToken = (JwtSecurityToken)validatedToken;
                var userName = jwtToken.Claims.First(x => x.Type == ClaimTypes.Name).Value;
                return userName;
            }
            catch
            {

                return null;
            }
        }
    }
}
