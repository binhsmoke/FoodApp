using MasJoheun.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;

namespace MasJoheun.Controllers
{
    [Route("/api/")]
    [ApiController]
    public class MemberController : ControllerBase
    {
        private readonly MasjoheunEntities db;
        public IConfiguration _configuration;
        public MemberController(IConfiguration config,MasjoheunEntities context)
        {
            db = context;
            _configuration = config;
        }
        [Route("check-client")]
        [HttpGet]
        public IActionResult CheckClient(string phone)
        {
            Client client = db.Clients.SingleOrDefault(p => p.Phone == phone);
            if (client != null)
                return Ok(new Message("ok"));
            else
                return BadRequest();
        }
        [Route("update-ordertime")]
        [HttpPost]
        public IActionResult UpdateOrdertime(string phone)
        {
            Client client = db.Clients.SingleOrDefault(p => p.Phone == phone);
            if (client != null)
            {
                client.OrderTime = client.OrderTime + 1;
                db.Clients.Update(client);
                db.SaveChanges();
                return Ok();
            }
            else
                return BadRequest();
        }
        [Route("create-client")]
        [HttpPost]
        public IActionResult CreateClient(Client client)
        {
           
            if (client != null)
            {
                var claims = new[] {
                    new Claim(JwtRegisteredClaimNames.Sub, _configuration["Jwt:Subject"]),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                    new Claim(JwtRegisteredClaimNames.Iat, DateTime.Now.ToString()),
                    new Claim("Name", client.Name),
                    new Claim("Phone", client.Phone)
                   };

                var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]));

                var signIn = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

                var token = new JwtSecurityToken(_configuration["Jwt:Issuer"], _configuration["Jwt:Audience"], claims, expires: DateTime.Now.AddDays(1), signingCredentials: signIn);

                db.Clients.Add(client);
                db.SaveChanges();
                return Ok(new Token(new JwtSecurityTokenHandler().WriteToken(token)));
            }
            else
                return BadRequest();
        }
        [Route("get-client")]
        [HttpGet]
        public IActionResult GetClient(string phone)
        {
            Client client = db.Clients.SingleOrDefault(p => p.Phone == phone);
            if (client != null)
                return Ok(client);
            else
                return BadRequest();
        }
        //Đăng nhập để lấy token
        [Route("login-client")]
        [HttpGet]
        public async Task<IActionResult> LoginClient(string phone, string password)
        {

            if (phone != null && password != null)
            {
                var client = await db.Clients.FirstOrDefaultAsync(user => user.Phone.ToLower() == phone.ToLower() && user.Password == password);

                if (client != null)
                {
                    //create claims details based on the user information
                    var claims = new[] {
                    new Claim(JwtRegisteredClaimNames.Sub, _configuration["Jwt:Subject"]),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                    new Claim(JwtRegisteredClaimNames.Iat, DateTime.Now.ToString()),
                    new Claim("Name", client.Name),
                    new Claim("Phone", client.Phone)
                   };

                    var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]));

                    var signIn = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

                    var token = new JwtSecurityToken(_configuration["Jwt:Issuer"], _configuration["Jwt:Audience"], claims, expires: DateTime.Now.AddDays(1), signingCredentials: signIn);

                    return Ok(new Token(new JwtSecurityTokenHandler().WriteToken(token)));
                }
                else
                {
                    return BadRequest("Invalid credentials");
                }
            }
            else
            {
                return BadRequest();
            }
        }
    }
}