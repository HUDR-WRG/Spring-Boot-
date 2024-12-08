let currentUser = null;

// 页面加载时检查登录状态
window.onload = function() {
    const user = localStorage.getItem('user');
    if (user) {
        currentUser = JSON.parse(user);
        showMainContent();
    }
};

// 显示/隐藏表单
function showLogin() {
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('registerForm').style.display = 'none';
}

function showRegister() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'block';
}

function showMainContent() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('mainContent').style.display = 'block';
    document.getElementById('userInfo').style.display = 'block';
    document.getElementById('username').textContent = currentUser.username;
    showLostItems();
}

// 登录注册相关函数
async function login() {
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch('/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });
        const result = await response.json();
        
        if (result.code === 200) {
            currentUser = result.data;
            localStorage.setItem('user', JSON.stringify(currentUser));
            showMainContent();
        } else {
            alert(result.msg || '登录失败');
        }
    } catch (error) {
        alert('登录失败：' + error.message);
    }
}

async function register() {
    const user = {
        username: document.getElementById('regUsername').value,
        password: document.getElementById('regPassword').value,
        email: document.getElementById('regEmail').value,
        phone: document.getElementById('regPhone').value
    };

    try {
        const response = await fetch('/user/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });
        const result = await response.json();
        
        if (result.code === 200) {
            alert('注册成功，请登录');
            showLogin();
        } else {
            alert(result.msg || '注册失败');
        }
    } catch (error) {
        alert('注册失败：' + error.message);
    }
}

function logout() {
    currentUser = null;
    localStorage.removeItem('user');
    document.getElementById('mainContent').style.display = 'none';
    document.getElementById('userInfo').style.display = 'none';
    showLogin();
}

// 物品相关函数
async function publishItem() {
    const item = {
        userId: currentUser.userId,
        itemName: document.getElementById('itemName').value,
        description: document.getElementById('description').value,
        category: document.getElementById('category').value,
        lostFound: document.getElementById('lostFound').value,
        location: document.getElementById('location').value,
        status: 'open'
    };

    try {
        const response = await fetch('/item/publish', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item)
        });
        const result = await response.json();
        
        if (result.code === 200) {
            alert('发布成功');
            document.getElementById('itemName').value = '';
            document.getElementById('description').value = '';
            document.getElementById('location').value = '';
            showLostItems();
        } else {
            alert(result.msg || '发布失败');
        }
    } catch (error) {
        alert('发布失败：' + error.message);
    }
}

async function showLostItems() {
    await loadItems('lost');
}

async function showFoundItems() {
    await loadItems('found');
}

async function showMyItems() {
    // 这里需要后端添加按用户ID查询的接口
    await loadItems(null, currentUser.userId);
}

async function loadItems(lostFound) {
    try {
        const url = new URL('/item/list', window.location.origin);
        if (lostFound) {
            url.searchParams.append('lostFound', lostFound);
        }
        
        const response = await fetch(url);
        const result = await response.json();
        
        if (result.code === 200) {
            displayItems(result.data);
        } else {
            alert(result.msg || '加载失败');
        }
    } catch (error) {
        alert('加载失败：' + error.message);
    }
}

function displayItems(items) {
    const container = document.getElementById('itemList');
    container.innerHTML = '';
    
    items.forEach(item => {
        const card = document.createElement('div');
        card.className = 'item-card';
        card.innerHTML = `
            <h3>${item.itemName}</h3>
            <p>${item.description}</p>
            <p>地点：${item.location}</p>
            <p>分类：${item.category}</p>
            <p>状态：${item.status}</p>
            ${item.userId !== currentUser.userId ? 
                `<button onclick="submitClaim(${item.itemId})">申请认领</button>` : 
                `<button onclick="deleteItem(${item.itemId})">删除</button>`}
        `;
        container.appendChild(card);
    });
}

async function submitClaim(itemId) {
    const claim = {
        itemId: itemId,
        claimerId: currentUser.userId,
        message: prompt('请输入认领说明：'),
        status: 'pending'
    };

    if (!claim.message) return;

    try {
        const response = await fetch('/claim/submit', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(claim)
        });
        const result = await response.json();
        
        if (result.code === 200) {
            alert('申请提交成功');
        } else {
            alert(result.msg || '申请提交失败');
        }
    } catch (error) {
        alert('申请提交失败：' + error.message);
    }
}

async function deleteItem(itemId) {
    if (!confirm('确定要删除这条信息吗？')) return;

    try {
        const response = await fetch(`/item/${itemId}`, {
            method: 'DELETE'
        });
        const result = await response.json();
        
        if (result.code === 200) {
            alert('删除成功');
            showMyItems();
        } else {
            alert(result.msg || '删除失败');
        }
    } catch (error) {
        alert('删除失败：' + error.message);
    }
} 