using MasJoheun.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;

namespace MasJoheun.Controllers
{   
    [Route("/api/")]
    [ApiController]
    public class FoodsController : ControllerBase
    {
        private readonly MasjoheunEntities db;
        public FoodsController(MasjoheunEntities context)
        {
            db = context;
        }
        [Authorize]
        [Route("get-food")]
        [HttpGet]
        public IActionResult GetFood(int idType)
        {
            var result = from f in db.Foods.ToList().Where(f=>f.IdType==idType) select f ;
            List<Food> foods = new List<Food>();
            foreach(Food food in result)
            {
                foods.Add(food);
            }
            if (foods != null)
                return Ok(foods);
            else
                return BadRequest();
        }
    }
}
