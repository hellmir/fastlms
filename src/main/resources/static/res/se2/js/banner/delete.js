function deleteSelectedBanners(event) {

    event.preventDefault();

    const selectedBanners = [];
    document.querySelectorAll('tbody input[type="checkbox"]:checked').forEach(checkbox => {
        const bannerRow = checkbox.closest('tr');
        const bannerName = bannerRow.querySelector('a').textContent;
        selectedBanners.push(bannerName);
    });

    if (selectedBanners.length === 0) {
        alert('삭제할 배너를 선택하세요.');
        return;
    }

    if (confirm('정말 삭제하시겠습니까?')) {

        fetch('/admin/banner/list.do', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(selectedBanners)
        })
        .then(response => {
            if (response.status === 204) {
                location.reload();
            } else {
                alert('배너 삭제에 실패했습니다.');
            }
        });

    }

}
